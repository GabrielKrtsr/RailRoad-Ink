import random
import math
import websockets
import asyncio
from typing import List, Tuple, Dict, Optional
import sys

from colorama import init as colorama_init
from colorama import Fore
from colorama import Style
colorama_init()

NB_SIMULATIONS = 1000
EXPLORATION_WEIGHT = 1.414
ID = "IA"
CLIENT = None
ROTATIONS = ["null", "R", "U", "L"]
THROWS = 0
BOARD = None
LAUNCH = False

SPECIAL_TILES = []
SPECIAL_TILES_USED = 0

class Interpreter:
    def __init__(self):
        """
        Initialise l'interpreteur avec la liste des commandes acceptees, et les tuiles tirées
        """
        self.commands = {}
        self.register_default_commands()
        self.current_tiles = []
    
    def register_command(self, command_name, function):
        """
        Permet d'enregistrer une nouvelle commande
        """
        self.commands[command_name.lower()] = function
    
    def register_default_commands(self):
        """
        Initialise les commandes par défaut
        """
        self.register_command("THROWS", self.throws)
        self.register_command("SCOREROUND", self.scoreround)
        self.register_command("BLAMES", self.blames)
    
    def throws(self, message):
        """
        Commande THROWS pour tirer les tuiles sur ce round
        """
        global THROWS
        THROWS += 1
        parts = message.strip().split()
        self.current_tiles = parts[2:6]
        asyncio.create_task(play_round_with_tiles(self.current_tiles))
    
    def scoreround(self, message):
        """
        Commande SCOREROUND pour récuperer le score total après chaque round
        """
        if message.strip().split()[2] == ID: 
            print(f"Score round {THROWS} : {message.strip().split()[3]}'")

    def blames(self, message):
        """
        Commande BLAMES pour savoir si l'IA se prend un blame
        """
        print(f"Le joueur {ID} prend un blames")
    
    def interpret(self, message):
        """
        Interpreteur pour executer les commandes 
        """
        words = message.strip().split()
        if len(words) < 2:
            return
        command = words[1].lower()
        if command in self.commands:
            self.commands[command](message)


class WebSocketClient:
    def __init__(self, uri):
        """
        Initialiser le websocket avec son URI 
        """
        self.uri = uri
        self.websocket = None
        self.interpreter = Interpreter()

    async def connect(self):
        """
        Permet de se connecter au websocket
        """
        try:
            self.websocket = await websockets.connect(self.uri)
            print("Connecté au serveur WebSocket!")
            asyncio.create_task(self.receive_messages())
        except Exception as e:
            print(f"Erreur de connexion : {e}")

    async def send_message(self, message):
        """
        Permet d'envoyer un message via le websocket
        """
        if self.websocket:
            try:
                await self.websocket.send(message)
                print(f"Message envoyé: {message}")
            except websockets.ConnectionClosed:
                print("Connexion fermée, impossible d'envoyer le message")

    async def receive_messages(self):
        """
        Permet de recevoir des messages du websocket
        """
        while self.websocket:
            try:
                response = await self.websocket.recv()
                print(f"Serveur: {response}")
                self.interpreter.interpret(response)
            except websockets.ConnectionClosed:
                print("Connexion fermée par le serveur")
                break
            
    async def close(self):
        """
        Permet de fermer la connexion au websocket
        """
        if self.websocket:
            await self.websocket.close()
            print("Déconnecté du serveur WebSocket")

class Tile:
    def __init__(self, id: str, name: str, connections: List[Tuple], is_special=False):
        """
        Initialise une tuile avec un identifiant et ses connexions.
        
        Args:
            id: Identifiant unique de la tuile
            connections: Liste de tuples représentant les connexions
                Pour des connexions standards: (côté1, côté2, type)
                Pour des chemins coupés: (côté1, type1, côté2, type2)
                Les côtés sont 'N', 'E', 'S', 'W' pour Nord, Est, Sud, Ouest
                Les types sont 'road' ou 'rail'
        """
        self.id = id
        self.name = name
        self.connections = connections
        self.rotations = 0 
        self.is_special = is_special
    
    def rotate(self):
        """
        Applique une rotation de 90° dans le sens horaire à la tuile
        """
        self.rotations = (self.rotations + 1) % 4
    
    def get_rotated_connections(self) -> List[Tuple]:
        """
        Retourne les connexions après rotation
        """
        if self.rotations == 0:
            return self.connections
        rotation_map = {
            0: {'N': 'N', 'E': 'E', 'S': 'S', 'W': 'W'},
            1: {'N': 'E', 'E': 'S', 'S': 'W', 'W': 'N'},
            2: {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'},
            3: {'N': 'W', 'E': 'N', 'S': 'E', 'W': 'S'}
        }
        rotated = []
        for conn in self.connections:
            if len(conn) == 3:
                c1, c2, conn_type = conn
                rotated.append((rotation_map[self.rotations][c1], rotation_map[self.rotations][c2], conn_type))
            elif len(conn) == 4:
                c1, type1, c2, type2 = conn
                rotated.append((rotation_map[self.rotations][c1], type1, rotation_map[self.rotations][c2], type2))
        return rotated
    
    def clone(self):
        """
        Permet de cloner une tuile
        """
        clone = Tile(self.id, self.name,self.connections.copy(),self.is_special)
        clone.rotations = self.rotations
        return clone


class Board:
    def __init__(self, size: int = 7):
        """
        Initialise un plateau de jeu de taille size x size
        
        Args:
            size: Taille du plateau (par défaut 7x7)
        """
        self.size = size
        self.grid = [[None for _ in range(size)] for _ in range(size)]
        self.exits = self.initialize_exits()
        self.connected_exits = set()
    
    def initialize_exits(self) -> Dict[str, List[Tuple[int, int, str]]]:
        """
        Initialise les sorties du plateau
        """
        exits = {
            'road': [],
            'rail': []
        }
        exits['road'].append((-1, 1, 'N'))
        exits['road'].append((-1, 5, 'N'))
        exits['road'].append((3, -1, 'W'))
        exits['road'].append((3, 7, 'E'))
        exits['road'].append((7, 1, 'S'))
        exits['road'].append((7, 5, 'S'))

        exits['rail'].append((-1, 3, 'N'))
        exits['rail'].append((1, -1, 'W'))
        exits['rail'].append((5, -1, 'W'))
        exits['rail'].append((1, 7, 'E'))
        exits['rail'].append((5, 7, 'E'))
        exits['rail'].append((7, 3, 'S'))   
        return exits
    
    def displayExits(self) :
        """
        Affiche les sorties du plateau
        """
        print(f"Road exits : {self.exits['road']}")
        print(f"Rail exits : {self.exits['rail']}")
    
    def is_valid_placement(self, row: int, col: int, tile: Tile) -> bool:
        """
        Vérifie si le placement d'une tuile est valide
        
        Args:
            row: Ligne du placement
            col: Colonne du placement
            tile: Tuile à placer
        
        Returns:
            bool: True si le placement est valide, False sinon
        """
        if not (0 <= row < self.size and 0 <= col < self.size):
            return False
        if self.grid[row][col] is not None:
            return False
        if self.is_first_turn():
            return self.is_adjacent_to_exit(row, col, tile)
        directions = {'N': (-1, 0), 'E': (0, 1), 'S': (1, 0), 'W': (0, -1)}
        opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
        rotated_connections = tile.get_rotated_connections()
        current_sides_with_types = {}
        for conn in rotated_connections:
            if len(conn) == 3:
                c1, c2, conn_type = conn
                current_sides_with_types[c1] = conn_type
                current_sides_with_types[c2] = conn_type
            elif len(conn) == 4:
                c1, type1, c2, type2 = conn
                current_sides_with_types[c1] = type1
                current_sides_with_types[c2] = type2
        current_sides = set(current_sides_with_types.keys())
        has_valid_adjacent = False
        for direction, (dr, dc) in directions.items():
            new_row, new_col = row + dr, col + dc
            if self.is_exit_position(new_row, new_col, direction):
                if direction in current_sides:
                    exit_type = self.get_exit_type(new_row, new_col, direction)
                    if current_sides_with_types[direction] == exit_type:
                        has_valid_adjacent = True
            elif 0 <= new_row < self.size and 0 <= new_col < self.size and self.grid[new_row][new_col] is not None:
                adjacent_tile = self.grid[new_row][new_col]
                adj_connections = adjacent_tile.get_rotated_connections()
                adj_sides_with_types = {}
                for conn in adj_connections:
                    if len(conn) == 3:
                        c1, c2, conn_type = conn
                        adj_sides_with_types[c1] = conn_type
                        adj_sides_with_types[c2] = conn_type
                    elif len(conn) == 4:
                        c1, type1, c2, type2 = conn
                        adj_sides_with_types[c1] = type1
                        adj_sides_with_types[c2] = type2
                adj_sides = set(adj_sides_with_types.keys())
                if direction in current_sides and opposite[direction] in adj_sides:
                    our_type = current_sides_with_types[direction]
                    adj_type = adj_sides_with_types[opposite[direction]]
                    if our_type == adj_type:
                        has_valid_adjacent = True
        if not has_valid_adjacent:
            return False
        connections_match = True
        for direction, (dr, dc) in directions.items():
            new_row, new_col = row + dr, col + dc
            if self.is_exit_position(new_row, new_col, direction):
                exit_type = self.get_exit_type(new_row, new_col, direction)
                if direction in current_sides:
                    if current_sides_with_types[direction] != exit_type:
                        connections_match = False
            elif 0 <= new_row < self.size and 0 <= new_col < self.size and self.grid[new_row][new_col] is not None:
                adjacent_tile = self.grid[new_row][new_col]
                adj_connections = adjacent_tile.get_rotated_connections()
                adj_sides_with_types = {}
                for conn in adj_connections:
                    if len(conn) == 3:
                        c1, c2, conn_type = conn
                        adj_sides_with_types[c1] = conn_type
                        adj_sides_with_types[c2] = conn_type
                    elif len(conn) == 4:
                        c1, type1, c2, type2 = conn
                        adj_sides_with_types[c1] = type1
                        adj_sides_with_types[c2] = type2
                adj_sides = set(adj_sides_with_types.keys())
                if direction in current_sides and opposite[direction] not in adj_sides:
                    connections_match = False
                if direction not in current_sides and opposite[direction] in adj_sides:
                    connections_match = False
                if direction in current_sides and opposite[direction] in adj_sides:
                    our_type = current_sides_with_types[direction]
                    adj_type = adj_sides_with_types[opposite[direction]]
                    if our_type != adj_type or our_type == None or adj_type == None :
                        connections_match = False
        return connections_match
    
    def place_tile(self, row: int, col: int, tile: Tile) -> None:
        """
        Place une tuile sur le plateau
        """
        if self.is_valid_placement(row, col, tile):
            self.grid[row][col] = tile
            self.update_connected_exits(row, col, tile)
    
    def is_first_turn(self):
        """
        Vérifie si c'est le premier tour
        """
        for row in range(self.size):
            for col in range(self.size):
                if self.grid[row][col] is not None:
                    return False
        return True

    def is_adjacent_to_exit(self, row: int, col: int, tile: Tile) -> bool:
        """Vérifie si la position est adjacente à une sortie"""
        directions = {'N': (-1, 0), 'E': (0, 1), 'S': (1, 0), 'W': (0, -1)}
        opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
        rotated_connections = tile.get_rotated_connections()
        current_sides = set()
        for conn in rotated_connections:
            if len(conn) >= 2:
                current_sides.add(conn[0])
                current_sides.add(conn[2])
        
        for direction, (dr, dc) in directions.items():
            new_row, new_col = row + dr, col + dc
            if self.is_exit_position(new_row, new_col, direction) and direction in current_sides and self.get_exit_type(new_row, new_col, direction) == self.get_connection_type(tile,direction) :
                return True

        return False

    def is_exit_position(self, row: int, col: int, direction: str) -> bool:
        """Vérifie si la position correspond à une sortie"""
        return (row,col,direction) in self.exits['rail'] or (row,col,direction) in self.exits['road']

    def get_exit_type(self, row: int, col: int, direction: str) -> Optional[str]:
        """
        Retourne le type de la sortie passée en argument
        """
        if (row,col,direction) in self.exits['road'] :
            return 'road'
        else:
            return 'rail'

    def get_connection_type(self, tile: Tile, direction: str) -> Optional[str]:
        """
        Retourne le type associé à la tuile passé en paramêtre pour la direction donnée
        """
        for conn in tile.get_rotated_connections():
            if len(conn) == 3:
                c1, c2, conn_type = conn
                if direction in [c1, c2]:
                    return conn_type
            elif len(conn) == 4:
                c1, type1, c2, type2 = conn
                if direction == c1:
                    return type1
                elif direction == c2:
                    return type2
        return None

    def update_connected_exits(self, row: int, col: int, tile: Tile) -> None:
        """
        Mets à jour les sorties connectées après placement d'une tuile
        
        Args:
            row: Ligne du placement
            col: Colonne du placement
            tile: Tuile placée
        """
        directions = {'N': (-1, 0), 'E': (0, 1), 'S': (1, 0), 'W': (0, -1)}
        for direction, (dr, dc) in directions.items():
            new_row, new_col = row + dr, col + dc
            if self.is_exit_position(new_row, new_col, direction):
                connection_type = self.get_connection_type(tile, direction)
                if connection_type is not None:
                    exit_type = self.get_exit_type(new_row, new_col, direction)
                    if connection_type == exit_type:
                        exit_key = f"{direction}_{new_row}_{new_col}"
                        self.connected_exits.add(exit_key)

    def score(self) -> int:
        """
        Calcule le score total du plateau selon les règles officielles
        
        Returns:
            int: Score total
        """
        networks_points = self.score_connected_networks()
        longest_road_points = self.find_longest_network('road')
        longest_rail_points = self.find_longest_network('rail')
        center_points = self.count_center_tiles()
        penalty_points = self.count_penalties()
        return networks_points + longest_road_points + longest_rail_points + center_points - penalty_points
    
    def score_connected_networks(self) -> int:
        """
        Calcule les points pour les réseaux connectés aux sorties
        
        Returns:
            int: Points pour les réseaux connectés
        """
        networks = self.identify_networks()
        total_points = 0
        for network_exits in networks:
            num_exits = len(network_exits)
            if num_exits >= 2:
                if num_exits == 2:
                    points = 4
                elif num_exits == 3:
                    points = 8
                elif num_exits == 4:
                    points = 12
                elif num_exits == 5:
                    points = 16
                elif num_exits == 6:
                    points = 20
                elif num_exits == 7:
                    points = 24
                elif num_exits == 8:
                    points = 28
                elif num_exits == 9:
                    points = 32
                elif num_exits == 10:
                    points = 36
                elif num_exits == 11:
                    points = 40
                else:
                    points = 45
                total_points += points
        return total_points
    
    def identify_networks(self) -> List[set]:
        """
        Identifie tous les réseaux sur le plateau et leurs sorties connectées
        
        Returns:
            List[Set[Tuple]]: Liste des ensembles de sorties connectées pour chaque réseau
        """
        visited_cells = {}
        networks = []
        opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
        connected_exit_cells = []
        for exit_type in ['road', 'rail']:
            for exit_pos in self.exits[exit_type]:
                exit_row, exit_col, exit_dir = exit_pos
                dr, dc = self.get_direction_delta(opposite[exit_dir])
                row, col = exit_row + dr, exit_col + dc
                if (0 <= row < self.size and 0 <= col < self.size and self.grid[row][col] is not None):
                    tile = self.grid[row][col]
                    opp_dir = self.get_opposite_direction(exit_dir)
                    conn_type = self.get_connection_type(tile, exit_dir)
                    if conn_type == exit_type:
                        connected_exit_cells.append((exit_row, exit_col, exit_dir, exit_type, row, col))
        for exit_row, exit_col, exit_dir, exit_type, start_row, start_col in connected_exit_cells:
            if (exit_row, exit_col, exit_dir) in visited_cells:
                continue
            network = set([(exit_row, exit_col, exit_dir)])
            visited_cells[(exit_row, exit_col, exit_dir)] = True
            queue = [(start_row, start_col, exit_type)]
            visited_path_cells = set([(start_row, start_col, exit_type)])
            i = 0
            opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
            while queue:
                i += 1
                r, c, network_type = queue.pop(0)
                for e_type in ['road', 'rail']:
                    for e_row, e_col, e_dir in self.exits[e_type]:
                        if (e_row, e_col, e_dir) in visited_cells:
                            #print(f" -> {(e_row, e_col, e_dir)} is in visited cells")
                            continue
                        dr, dc = self.get_direction_delta(opposite[e_dir])
                        if r == e_row + dr and c == e_col + dc:
                            tile = self.grid[r][c]
                            opp_dir = self.get_opposite_direction(e_dir)
                            conn_type = self.get_connection_type(tile, e_dir)
                            if conn_type == e_type:
                                network.add((e_row, e_col, e_dir))
                                visited_cells[(e_row, e_col, e_dir)] = True
                tile = self.grid[r][c]
                rotated_connections = tile.get_rotated_connections()
                for conn in rotated_connections:
                    if len(conn) == 3:
                        c1, c2, conn_type = conn
                        if not tile.id.startswith('S') :
                            if conn_type == network_type:
                                self._add_connected_cells(r, c, c1, conn_type, queue, visited_path_cells, display=False)
                                self._add_connected_cells(r, c, c2, conn_type, queue, visited_path_cells, display=False)
                        else : 
                            self._add_connected_cells(r, c, c1, conn_type, queue, visited_path_cells, display=False)
                            self._add_connected_cells(r, c, c2, conn_type, queue, visited_path_cells, display=False)
                    elif len(conn) == 4:
                        c1, type1, c2, type2 = conn
                        if not tile.id.startswith('S') : 
                            if type1 == network_type:
                                self._add_connected_cells(r, c, c1, type1, queue, visited_path_cells, display=False)
                            if type2 == network_type:
                                self._add_connected_cells(r, c, c2, type2, queue, visited_path_cells, display=False)
                        else : 
                            self._add_connected_cells(r, c, c1, type1, queue, visited_path_cells, display=False)
                            self._add_connected_cells(r, c, c2, type2, queue, visited_path_cells, display=False)
            if len(network) >= 2:
                networks.append(network)
        return networks

    def _add_connected_cells(self, r, c, direction, conn_type, queue, visited, display=False):
        """
        Ajoute les cellules connectées à une file d'attente pour l'exploration d'un réseau de connexions. On examine une cellule donnée et tente d'ajouter les cellules adjacentes
        qui respectent les critères de connexion au réseau.
        """
        if display : 
            print(f"{Fore.CYAN}\n Call CONNECTED CELL for ({r}.{c}) in dir {direction} for con {conn_type}:{Style.RESET_ALL}")
        opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
        dr, dc = self.get_direction_delta(direction)
        nr, nc = r + dr, c + dc
        if not (0 <= nr < self.size and 0 <= nc < self.size) or self.grid[nr][nc] is None :
            if (nr,nc,direction) in self.exits[conn_type] :
                if (nr, nc, conn_type) in visited:
                    if display : 
                        print(f"Cellule ({nr},{nc}) déjà visitée.")
                    return
                adj_conn_type = conn_type
                if adj_conn_type == conn_type:
                    if display : 
                        print(f"Ajout de la cellule adjacente ({nr},{nc}) au réseau.")
                        print(f" -> Taille avant : {len(queue)}")
                        #queue.append((nr, nc, conn_type))
                        print(f" -> Taille après : {len(queue)}")
                        #visited.add((nr, nc, conn_type))
                    return
            else : 
                if display : 
                    print(f"Cellule ({nr},{nc}) pour direction {direction} hors des limites ou sans tuile.")
                return
        if (nr, nc, conn_type) in visited:
            if display : 
                print(f"Cellule ({nr},{nc}) déjà visitée.")
            return
        adj_tile = self.grid[nr][nc]
        opp_dir = self.get_opposite_direction(direction)
        adj_conn_type = self.get_connection_type(adj_tile, opp_dir)
        if adj_conn_type == conn_type:
            if display : 
                print(f"Ajout de la cellule adjacente ({nr},{nc}) au réseau.")
                print(f" -> 1. Taille avant : {len(queue)}")
            queue.append((nr, nc, conn_type))
            if display :
                print(f" ->1. Taille après : {len(queue)}")
            visited.add((nr, nc, conn_type))
    
    def get_direction_delta(self, direction):
        """
        Retourne le delta de coordonnées pour une direction donnée
        """
        direction_deltas = {
            'N': (-1, 0),
            'E': (0, 1),
            'S': (1, 0),
            'W': (0, -1)
        }
        return direction_deltas[direction]
    
    def get_opposite_direction(self, direction):
        """
        Retourne la direction opposée
        """
        opposites = {
            'N': 'S',
            'E': 'W',
            'S': 'N',
            'W': 'E'
        }
        return opposites[direction]

    def find_longest_network(self, network_type: str) -> int:
        """
        Trouve la longueur du plus long chemin continu d'un type donné
        """
        max_length = 0
        visited = set()
        for row in range(self.size):
            for col in range(self.size):
                if self.grid[row][col] is not None and (row, col) not in visited:
                    paths = self.find_all_paths(row, col, network_type, visited)
                    for path in paths:
                        max_length = max(max_length, len(path))
        return max_length
    
    def find_all_paths(self, start_row, start_col, network_type, global_visited):
        """
        Trouve tous les chemins possibles à partir d'un point de départ
        """
        key = (start_row, start_col, network_type)
        if key in global_visited:
            return []
        
        tile = self.grid[start_row][start_col]
        if tile is None:
            return []
        has_network_type = False
        if tile.id.startswith('S'):
            has_network_type = True
        elif tile.id.startswith('M'):
            has_network_type = True
        elif (network_type == 'road' and tile.id.startswith('R')) or \
            (network_type == 'rail' and tile.id.startswith('T')):
            has_network_type = True
        else:
            for conn in tile.get_rotated_connections():
                if len(conn) == 3 and conn[2] == network_type:
                    has_network_type = True
                    break
                elif len(conn) == 4 and (conn[1] == network_type or conn[3] == network_type):
                    has_network_type = True
                    break
        if not has_network_type:
            return []
        global_visited.add(key)
        directions = {'N': (-1, 0), 'E': (0, 1), 'S': (1, 0), 'W': (0, -1)}
        opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
        connections = []
        rotated_connections = tile.get_rotated_connections()
        for conn in rotated_connections:
            if len(conn) == 3:
                c1, c2, conn_type = conn
                if conn_type == network_type:
                    connections.append((c1, c2))
            elif len(conn) == 4:
                c1, type1, c2, type2 = conn
                if type1 == network_type and type2 == network_type:
                    connections.append((c1, c2))
                elif type1 == network_type:
                    connections.append((c1, None))
                elif type2 == network_type:
                    connections.append((None, c2))
        if not connections and (tile.id.startswith('S') or tile.id.startswith('M')):
            for direction in directions:
                dr, dc = directions[direction]
                new_row, new_col = start_row + dr, start_col + dc
                if not (0 <= new_row < self.size and 0 <= new_col < self.size):
                    continue
                if self.grid[new_row][new_col] is None:
                    continue
                adj_tile = self.grid[new_row][new_col]
                opp_dir = opposite[direction]
                adj_conn_type = self.get_connection_type(adj_tile, opp_dir)
                if adj_conn_type == network_type:
                    connections.append((direction, None))
        if not connections:
            return [[(start_row, start_col)]]
        all_paths = []
        for c1, c2 in connections:
            for direction in [c1, c2]:
                if direction is None:
                    continue
                dr, dc = directions[direction]
                new_row, new_col = start_row + dr, start_col + dc
                if not (0 <= new_row < self.size and 0 <= new_col < self.size):
                    if self.is_exit_position(new_row, new_col, direction) and \
                    self.get_exit_type(new_row, new_col, direction) == network_type:
                        all_paths.append([(start_row, start_col)])
                    continue
                if (new_row, new_col, network_type) in global_visited or self.grid[new_row][new_col] is None:
                    continue
                adj_tile = self.grid[new_row][new_col]
                opp_direction = opposite[direction]
                adj_conn_type = self.get_connection_type(adj_tile, opp_direction)
                if adj_tile.id.startswith('S') or adj_tile.id.startswith('M'):
                    if adj_conn_type is not None:
                        new_global_visited = global_visited.copy()
                        sub_paths = self.find_all_paths(new_row, new_col, network_type, new_global_visited)
                        for path in sub_paths:
                            all_paths.append([(start_row, start_col)] + path)
                elif adj_conn_type == network_type:
                    new_global_visited = global_visited.copy()
                    sub_paths = self.find_all_paths(new_row, new_col, network_type, new_global_visited)
                    for path in sub_paths:
                        all_paths.append([(start_row, start_col)] + path)
        if not all_paths:
            all_paths.append([(start_row, start_col)])
        return all_paths
    
    def contains_network_type(self, tile: Tile, network_type: str) -> bool:
        """
        Vérifie si une tuile contient un type de réseau spécifique
        """
        if tile.id.startswith('S'):
            return True
        if tile.id.startswith('M'):
            return True
        if network_type == 'road' and tile.id.startswith('R'):
            return True
        if network_type == 'rail' and tile.id.startswith('T'):
            return True
        for conn in tile.get_rotated_connections():
            if len(conn) >= 3:
                if (len(conn) == 3 and conn[2] == network_type) or \
                (len(conn) == 4 and (conn[1] == network_type or conn[3] == network_type)):
                    return True
        return False

    def count_center_tiles(self) -> int:
        """
        Compte le nombre de cellules centrales
        """
        center_size = 3
        center_start = (self.size - center_size) // 2
        center_end = center_start + center_size
        center_count = 0
        for row in range(center_start, center_end):
            for col in range(center_start, center_end):
                if self.grid[row][col] is not None:
                    center_count += 1
        return center_count
    
    def count_penalties(self) -> int:
        """
        Compte le nombre de pénalités pour fausse route
        """
        penalties = 0
        directions = {'N': (-1, 0), 'E': (0, 1), 'S': (1, 0), 'W': (0, -1)}
        for row in range(self.size):
            for col in range(self.size):
                tile = self.grid[row][col]
                if tile is None:
                    continue
                rotated_connections = tile.get_rotated_connections()
                sides_with_types = {}
                for conn in rotated_connections:
                    if len(conn) == 3:
                        c1, c2, conn_type = conn
                        sides_with_types[c1] = conn_type
                        sides_with_types[c2] = conn_type
                    elif len(conn) == 4:
                        c1, type1, c2, type2 = conn
                        sides_with_types[c1] = type1
                        sides_with_types[c2] = type2
                for direction, conn_type in sides_with_types.items():
                    dr, dc = directions[direction]
                    new_row, new_col = row + dr, col + dc
                    connected = False
                    if (new_row < 0 or new_row >= self.size or new_col < 0 or new_col >= self.size):
                        opposite_dir = self.get_opposite_direction(direction)
                        for exit_coords in self.exits[conn_type]:
                            e_row, e_col, e_dir = exit_coords
                            if (e_row == new_row and e_col == new_col and e_dir == direction):
                                connected = True
                                break
                    elif (0 <= new_row < self.size and 0 <= new_col < self.size and self.grid[new_row][new_col] is not None):
                        adj_tile = self.grid[new_row][new_col]
                        opposite_side = self.get_opposite_direction(direction)
                        adj_conn_type = self.get_connection_type(adj_tile, opposite_side)
                        if adj_conn_type is not None and adj_conn_type == conn_type:
                            connected = True
                    if new_row < 0 or new_row >= self.size or new_col < 0 or new_col >= self.size :
                        connected = True
                    if not connected:
                        penalties += 1
        return penalties
    
    def clone(self):
        """
        Permet de cloner le plateau de jeu
        """
        clone = Board(self.size)
        for i in range(self.size):
            for j in range(self.size):
                if self.grid[i][j] is not None:
                    clone.grid[i][j] = self.grid[i][j].clone()
        clone.connected_exits = self.connected_exits.copy()
        return clone

class GameState:
    
    def __init__(self, board: Board, available_tiles: List[Tile], turns_left: int, 
                 special_tiles_available: List[Tile] = None, 
                 special_tiles_used_this_turn: bool = False,
                 special_tiles_used_count: int = 0):
        """
        Initialise un état de jeu
        """
        self.board = board
        self.available_tiles = available_tiles
        self.turns_left = turns_left

        self.special_tiles_available = special_tiles_available or []
        self.special_tiles_used_this_turn = special_tiles_used_this_turn
        self.special_tiles_used_count = special_tiles_used_count    
    
    def get_legal_moves(self) -> List[Tuple[int, int, Tile, int]]:
        """
        Retourne tous les coups légaux possibles
        """
        legal_moves = []
        for tile in self.available_tiles:
            tile_copy = tile.clone()
            for rotation in range(4):
                tile_copy.rotations = rotation    
                for row in range(self.board.size):
                    for col in range(self.board.size):
                        if self.board.is_valid_placement(row, col, tile_copy):
                            legal_moves.append((row, col, tile_copy.clone(), rotation))
        if not self.special_tiles_used_this_turn and self.special_tiles_used_count < 3:
            for tile in self.special_tiles_available:
                tile_copy = tile.clone()
                for rotation in range(4):
                    tile_copy.rotations = rotation    
                    for row in range(self.board.size):
                        for col in range(self.board.size):
                            if self.board.is_valid_placement(row, col, tile_copy):
                                legal_moves.append((row, col, tile_copy.clone(), rotation))
        return legal_moves
    
    def apply_move(self, move: Tuple[int, int, Tile, int]) -> 'GameState':
        """
        Applique un coup et retourne le nouvel état
        """
        row, col, tile, rotation = move
        new_board = self.board.clone()

        if tile.is_special:
            new_special_tiles = [t.clone() for t in self.special_tiles_available if t.id != tile.id]
            new_tiles = [t.clone() for t in self.available_tiles]
            special_used_this_turn = True
            special_used_count = self.special_tiles_used_count + 1
        else:
            new_special_tiles = [t.clone() for t in self.special_tiles_available]
            new_tiles = [t.clone() for t in self.available_tiles if t.id != tile.id]
            special_used_this_turn = self.special_tiles_used_this_turn
            special_used_count = self.special_tiles_used_count
        
        tile.rotations = rotation
        new_board.place_tile(row, col, tile)
        
        return GameState(
            new_board, 
            new_tiles, 
            self.turns_left - 1,
            new_special_tiles,
            special_used_this_turn,
            special_used_count
        )
    
    def is_terminal(self) -> bool:
        """
        Vérifie si l'état est terminal
        """
        return self.turns_left == 0 or len(self.available_tiles) == 0
    
    def score(self) -> int:
        """
        Retourne le score du plateau associé
        """
        return self.board.score()
    
    def clone(self) -> 'GameState':
        """
        Permet de cloner l'état de jeu
        """
        return GameState(
            self.board.clone(),
            [t.clone() for t in self.available_tiles],
            self.turns_left,
            [t.clone() for t in self.special_tiles_available],
            self.special_tiles_used_this_turn,
            self.special_tiles_used_count
        )

class MCTSNode:
    def __init__(self, state: GameState, parent=None, move=None):
        """
        Initialise un nœud de l'arbre MCTS
        """
        self.state = state
        self.parent = parent
        self.move = move
        self.children = []
        self.visits = 0
        self.value = 0
        self.untried_moves = state.get_legal_moves()
        
    def is_fully_expanded(self) -> bool:
        """
        Vérifie si tous les coups ont été essayés
        """
        return len(self.untried_moves) == 0
    
    def is_terminal(self) -> bool:
        """
        Vérifie si l'état est terminal
        """
        return self.state.is_terminal()
    
    def best_child(self, exploration_weight: float) -> 'MCTSNode':
        """
        Sélectionne le meilleur enfant selon la formule UCB1
        """
        if not self.children:
            return None
        choices_weights = [(child.value / child.visits) + exploration_weight * math.sqrt((2 * math.log(self.visits) / child.visits)) for child in self.children]
        return self.children[choices_weights.index(max(choices_weights))]
    
    def expand(self) -> 'MCTSNode':
        """
        Étend l'arbre en ajoutant un enfant
        """
        if not self.untried_moves:
            return None
        move = random.choice(self.untried_moves)
        self.untried_moves.remove(move)
        new_state = self.state.apply_move(move)
        child = MCTSNode(new_state, parent=self, move=move)
        self.children.append(child)
        return child
    
    def update(self, result: float) -> None:
        """
        Met à jour la valeur et le nombre de visites du noeud
        """
        self.visits += 1
        self.value += result


def mcts_search(state: GameState, num_simulations: int = NB_SIMULATIONS) -> Tuple[int, int, Tile, int]:
    """
    Effectue une recherche MCTS pour trouver le meilleur coup
    """
    root = MCTSNode(state)
    for _ in range(num_simulations):
        node = root
        while not node.is_terminal() and node.is_fully_expanded():
            next_node = node.best_child(EXPLORATION_WEIGHT)
            if next_node is None:
                break
            node = next_node
        if not node.is_terminal():
            expanded_node = node.expand()
            if expanded_node is not None:
                node = expanded_node
        state_to_simulate = node.state.clone()
        while not state_to_simulate.is_terminal():
            possible_moves = state_to_simulate.get_legal_moves()
            if not possible_moves:
                break
            move = random.choice(possible_moves)
            state_to_simulate = state_to_simulate.apply_move(move)
        result = state_to_simulate.score()
        while node is not None:
            node.update(result)
            node = node.parent
    if not root.children:
        return None
    best_child = max(root.children, key=lambda c: c.visits)
    return best_child.move

def convert_name_to_special_tile(tile_name):
    """
    Retourne une tuile spéciale à partir de son nom
    """
    special_tiles = generate_special_tiles()
    for tile in special_tiles:
        if tile.name == tile_name:
            return tile
    print(f"Tuile spéciale inconnue: {tile_name}")
    return None

def convert_name_to_tile(tile_name):
    """
    Retourne une tuile à partir de son nom
    """
    all_tiles = generate_standard_tiles()
    for tile in all_tiles:
        if tile.name == tile_name:
            return tile
    print(f"Tuile inconnue: {tile_name}, utilisation d'une tuile par défaut")
    return all_tiles[0].clone()

async def play_round_with_tiles(tile_names):
    """
    Joue un round avec les tuiles tirées
    """
    global BOARD
    global SPECIAL_TILES_USED
    global SPECIAL_TILES

    tilesPlaced = 0
    special_tile_used_this_turn = False
    
    if BOARD is None:
        #print(f"{Fore.RED} Init specials tiles {Style.RESET_ALL}")
        BOARD = Board()
        SPECIAL_TILES_USED = 0
        SPECIAL_TILES = generate_special_tiles()
    
    board = BOARD
    available_tiles = [convert_name_to_tile(name) for name in tile_names]
    
    for placement_num in range(1, len(available_tiles) + 1):
        if not available_tiles:
            break
        current_state = GameState(
            board, 
            available_tiles, 
            1,
            SPECIAL_TILES,
            special_tile_used_this_turn,
            SPECIAL_TILES_USED  
        )
        
        best_move = mcts_search(current_state, NB_SIMULATIONS)
        
        if best_move is None:
            print(f"  Placement {placement_num}: Aucun coup légal possible")
            break
        
        row, col, tile, rotation = best_move

        #print(f"{Fore.CYAN} Use of a tile : {tile.name} / {tile.is_special} {Style.RESET_ALL}")

        if tile.is_special:
            SPECIAL_TILES = [t for t in SPECIAL_TILES if t.id != tile.id]
            special_tile_used_this_turn = True
            SPECIAL_TILES_USED += 1
            #print(f"  Placement d'une tuile spéciale: {tile.name} ({SPECIAL_TILES_USED}/3 utilisées)")
        else:
            available_tiles = [t for t in available_tiles if t.id != tile.id]
        
        tile.rotations = rotation
        board.place_tile(row, col, tile)
        await CLIENT.send_message(f"{ID} PLACES {tile.name} {ROTATIONS[rotation]} {chr(65+col)}{row+1}")
        tilesPlaced += 1
    
    intermediate_score = board.score()
    #display_detailed_score(board)
    if tilesPlaced < 5 : 
        await CLIENT.send_message(f"{ID} YIELDS")
    return board

def generate_standard_tiles() -> List[Tile]:
    """
    Génère les tuiles utilisées
    """
    tiles = []
    tiles.append(Tile("R1", "H", [("N", "S", "road")]))
    tiles.append(Tile("R2", "Hc", [("N", "E", "road")]))
    tiles.append(Tile("R3", "Hj", [("N", "E", "road"), ("S", "E", "road"), ("N", "S", "road")]))
    # tiles.append(Tile("R4", "HH", [("N", "S", "road"), ("E", "W", "road")]))
    tiles.append(Tile("T1", "R", [("N", "S", "rail")]))
    tiles.append(Tile("T2", "Rc", [("N", "E", "rail")]))
    tiles.append(Tile("T3", "Rj", [("N", "E", "rail"), ("S", "E", "rail"), ("N", "S", "rail")]))
    # tiles.append(Tile("T4", "RR", [("N", "S", "rail"), ("E", "W", "rail")]))
    tiles.append(Tile("S1", "S", [("N", "road", "S", "rail")]))
    tiles.append(Tile("S2", "Sc", [("N", "road", "E", "rail")]))
    tiles.append(Tile("M1", "HR", [("N", "S", "road"), ("E", "W", "rail")]))
    # tiles.append(Tile("S3", "SH", [("N", "road", "S", "road"), ("N","road","E","road"), ("S","road","E","road"), ("N","road","W","rail"), ("S","road","W","rail"), ("W","rail","E","road")]))
    # tiles.append(Tile("S4", "SHR", [("N", "road", "S", "road"), ("N","road","E","rail"), ("S","road","E","rail"), ("N","road","W","rail"), ("S","road","W","rail"), ("W","rail","E","rail")]))
    # tiles.append(Tile("S5", "SR", [("N", "rail", "S", "rail"), ("N","rail","E","rail"), ("S","road","E","road"), ("N","rail","W","road"), ("S","rail","W","road"), ("W","road","E","rail")]))
    # tiles.append(Tile("S6", "SS", [("N", "road", "S", "rail"), ("N","road","E","road"), ("S","rail","E","road"), ("N","road","W","rail"), ("S","rail","W","rail"), ("W","rail","E","road")]))    
    return tiles

def generate_special_tiles() -> List[Tile]:
    """
    Génère les tuiles spéciales du jeu
    """
    special_tiles = []
    special_tiles.append(Tile("R4", "HH", [("N", "S", "road"), ("E", "W", "road"), ("N", "W", "road"), ("N", "E", "road"), ("E", "S", "road"), ("S", "W", "road")],True))
    special_tiles.append(Tile("S3", "SH", [("N", "road", "S", "road"), ("N","road","E","road"), ("S","road","E","road"), ("N","road","W","rail"), ("S","road","W","rail"), ("W","rail","E","road")],True))
    special_tiles.append(Tile("S4", "SHR", [("N", "road", "S", "road"), ("N","road","E","rail"), ("S","road","E","rail"), ("N","road","W","rail"), ("S","road","W","rail"), ("W","rail","E","rail")],True))
    special_tiles.append(Tile("S5", "SR", [("N", "rail", "S", "rail"), ("N","rail","E","rail"), ("S","road","E","road"), ("N","rail","W","road"), ("S","rail","W","road"), ("W","road","E","rail")],True))
    special_tiles.append(Tile("S6", "SS", [("N", "road", "S", "rail"), ("N","road","E","road"), ("S","rail","E","road"), ("N","road","W","rail"), ("S","rail","W","rail"), ("W","rail","E","road")],True))    
    special_tiles.append(Tile("T4", "RR", [("N", "S", "rail"), ("E", "W", "rail"), ("N", "W", "rail"), ("N", "E", "rail"), ("E", "S", "rail"), ("S", "W", "rail")],True))
    return special_tiles

def display_detailed_score(board: Board):
    """
    Affiche le score détaillé
    """
    exit_points = board.score_connected_networks()
    longest_road = board.find_longest_network('road')
    longest_rail = board.find_longest_network('rail')
    center_points = board.count_center_tiles()
    penalties = board.count_penalties()
    total_score = exit_points + longest_road + longest_rail + center_points - penalties
    print("\nDétail du score:")
    print(f"  Sorties connectées: {exit_points} points")
    print(f"  Réseau routier le plus long: {longest_road} points")
    print(f"  Réseau ferroviaire le plus long: {longest_rail} points")
    print(f"  Tuiles centrales: {center_points} points")
    print(f"  Pénalités: -{penalties} points")
    print(f"  Score total: {total_score} points")

def best_move_suggestion(board: Board, available_tiles: List[Tile]) -> Tuple[int, int, Tile, int]:
    """
    Suggère le meilleur coup à jouer et affiche toutes les possibilités
    """
    state = GameState(board, available_tiles, 1)
    all_moves = state.get_legal_moves()    
    move_scores = []
    for move in all_moves:
        row, col, tile, rotation = move
        test_board = board.clone()
        test_tile = tile.clone()
        test_tile.rotations = rotation
        test_board.place_tile(row, col, test_tile)
        score = test_board.score()
        move_scores.append((move, score))    
    best_move = mcts_search(state, NB_SIMULATIONS)
    if best_move:
        row, col, tile, rotation = best_move
        directions = {'N': (-1, 0), 'E': (0, 1), 'S': (1, 0), 'W': (0, -1)}
        opposite = {'N': 'S', 'E': 'W', 'S': 'N', 'W': 'E'}
        test_tile = tile.clone()
        test_tile.rotations = rotation
        rotated_connections = test_tile.get_rotated_connections()
        for direction, (dr, dc) in directions.items():
            new_row, new_col = row + dr, col + dc
            if 0 <= new_row < board.size and 0 <= new_col < board.size:
                adjacent_tile = board.grid[new_row][new_col]
                if adjacent_tile is not None:
                    our_connection_type = None
                    for conn in rotated_connections:
                        if len(conn) == 3 and direction in conn[:2]:
                            our_connection_type = conn[2]
                        elif len(conn) == 4 and direction == conn[0]:
                            our_connection_type = conn[1]
                        elif len(conn) == 4 and direction == conn[2]:
                            our_connection_type = conn[3]
                    adj_rotated_connections = adjacent_tile.get_rotated_connections()
                    adj_connection_type = None
                    for conn in adj_rotated_connections:
                        if len(conn) == 3 and opposite[direction] in conn[:2]:
                            adj_connection_type = conn[2]
                        elif len(conn) == 4 and opposite[direction] == conn[0]:
                            adj_connection_type = conn[1]
                        elif len(conn) == 4 and opposite[direction] == conn[2]:
                            adj_connection_type = conn[3]
                    match = our_connection_type == adj_connection_type if our_connection_type and adj_connection_type else False
            elif board.is_exit_position(new_row, new_col, direction):
                exit_type = board.get_exit_type(new_row, new_col, direction)
                our_connection_type = None
                for conn in rotated_connections:
                    if len(conn) == 3 and direction in conn[:2]:
                        our_connection_type = conn[2]
                    elif len(conn) == 4 and direction == conn[0]:
                        our_connection_type = conn[1]
                    elif len(conn) == 4 and direction == conn[2]:
                        our_connection_type = conn[3]
                match = our_connection_type == exit_type if our_connection_type else False
        test_board = board.clone()
        test_tile = tile.clone()
        test_tile.rotations = rotation
        test_board.place_tile(row, col, test_tile)
        #display_detailed_score(test_board)
    else:
        print("Aucun coup possible")
    return best_move

def display_board(board: Board) -> None:
    """
    Affiche le plateau
    """
    tile_chars = {
        None: '·',
        'R': 'R',
        'T': 'T',
        'M': 'X',
        'S': 'S'
    }
    print("  " + " ".join([str(i) for i in range(board.size)]))
    for i in range(board.size):
        row_str = f"{i} "
        for j in range(board.size):
            tile = board.grid[i][j]
            if tile is None:
                row_str += tile_chars[None] + " "
            else:
                row_str += tile.id[0] + " "
        print(row_str)

async def main():
    """
    Fonction principale
    """
    print("=== Railroad Ink avec MCTS ===")
    print("En attente des commandes THROWS...")
    while True:
        await asyncio.sleep(1)

async def smain():
    global ID
    if(len(sys.argv) < 2) : 
        print("Need to specify an ID")
        return
    ID = sys.argv[1]
    LAUNCH = False
    if(len(sys.argv) > 2) : 
        LAUNCH = sys.argv[2]
    global CLIENT
    CLIENT = WebSocketClient("ws://localhost:8000")
    await CLIENT.connect()
    await CLIENT.send_message(ID + " ENTERS")
    await CLIENT.send_message(ID + " ELECTS " + ID)
    if LAUNCH == "launch" : 
        await CLIENT.send_message(ID + " PLAYS ")
    await main()
    await CLIENT.close()

if __name__ == "__main__":
    #mainbis()
    asyncio.run(smain())
