import interpreter from "./interpreterJeu.js";
import {addMessage} from './messages.js'

const svg = document.getElementById("svgD");
let specials = 0;
let totalSpecials = 0;
let name = null;
let id = null;
let mouvements = {}
let actions = Array()
let round = 0;
let socket = null;
let rotations = {
    "0" : "null",
    "-90" : "L", 
    "90" : "R",
    "-180": "U",
    "180" : "U",
    "270": "L",
    "-270": "U"
}
let selectedImage = null;
let numberOfTIlesToPlace = 0;
let numberOfSpecialTilesAllowed = 1;

let messageQueue = [];
let isProcessing = false;

function resetClassicTiles() {
    numberOfTIlesToPlace = 0;
}

function resetSpecialTiles() {
    if(totalSpecials < 3) {
        numberOfSpecialTilesAllowed = 1;
        document.querySelectorAll('.specials img').forEach(img => {
            img.setAttribute('draggable', 'true');
        });
    }
}

function removeSpecialTile() {
    numberOfSpecialTilesAllowed += 1;
    document.querySelectorAll('.specials img').forEach(img => {
        img.setAttribute('draggable', 'true');
    });
}

function moveSpecialTile() {
    numberOfSpecialTilesAllowed -= 1;
    document.querySelectorAll('.specials img').forEach(img => {
        img.setAttribute('draggable', 'false');
    });
}

function processMessageQueue() {
    if (messageQueue.length > 0 && !isProcessing) {
        isProcessing = true;
        let nextMessage = messageQueue.shift();
        interpreter.interpret(nextMessage)
            .then(() => {
                isProcessing = false;
                processMessageQueue();
            })
            .catch((error) => {
                console.error("Erreur de traitement du message :", error);
                isProcessing = false;
                processMessageQueue();
            });
    }
}

function initSpecialsTiles() {
    fetch('/api/svg-files')
      .then(response => response.json())
      .then(fichiers => {
        fichiers.forEach(fichier => {
          const image = new Image();
          image.src = `/images/tiles/specials/${fichier}`;
          image.width = '50';
          image.height = '50';
          image.draggable = false;
          image.id = fichier.split(".")[0];
          image.setAttribute("data-special","true");
          image.setAttribute("data-flipped","false");
          image.setAttribute("data-fixed","false");
        //   image.setAttribute("data-special","false");
          image.setAttribute("draggable","false");
          image.setAttribute("data-rotation",0);
          image.setAttribute("data-tile", fichier.split(".")[0]);
          image.classList.add('special-tile');
          document.querySelector('.specials').appendChild(image);
          console.log(`Image SVG ajoutée: ${fichier}`);
        });
      })
      .catch(error => {
        console.error('Erreur lors du chargement des images SVG:', error);
      });
  }

function getActions() {
    return actions;
}

function decrementNumberOfTilesToPlace() {
    numberOfTIlesToPlace -= 1;
}

function incrementNumberOfTilesToPlace() {
    numberOfTIlesToPlace += 1;
}
 
function getNumberOfTilesToPlace() {
    return numberOfTIlesToPlace;
}

function addRound() {
    round += 1;
    return round;
}

function getRound() {
    return round;
}

function getID() {
    return id;
}

function getMousePosition(event) {
    if (svg && svg.getScreenCTM()) {
        let point = svg.createSVGPoint();
        point.x = event.clientX;
        point.y = event.clientY;

        let svgPoint = point.matrixTransform(svg.getScreenCTM().inverse());

        return { x: svgPoint.x, y: svgPoint.y };
    } else {
        console.warn("SVG ou getScreenCTM n'est pas disponible.");
        return { x: 0, y: 0 };
    }
}

function getCellFromCoordinates(x, y) {
    const cells = document.querySelectorAll(".cell");

    for (let cell of cells) {
        let transform = cell.getAttribute("transform");
        if (transform) {
            let match = transform.match(/translate\((\d+),(\d+)\)/);
            if (match) {
                let cellX = parseInt(match[1]);
                let cellY = parseInt(match[2]);

                if (x >= cellX && x < cellX + 244 && y >= cellY && y < cellY + 244) {
                    return cell.id;
                }
            }
        }
    }
    return null;
}

function flipImage() {
    if (!selectedImage) return;

    let flipped = selectedImage.getAttribute("data-flipped") === "true";
    let scaleX = flipped ? 1 : -1;
    let bbox = selectedImage.getBBox();
    let cx = bbox.x + bbox.width / 2;

    let prevRotation = selectedImage.getAttribute("transform");
    selectedImage.setAttribute("transform", `scale(${scaleX}, 1) translate(${scaleX === -1 ? -bbox.width - 2 * bbox.x : 0}, 0) ${prevRotation}`);
    selectedImage.setAttribute("data-flipped", flipped ? "false" : "true");
    console.warn(`New flip : ${selectedImage.getAttribute("data-flipped")}`)
}

function rotateImage(degrees) {
    if (!selectedImage || selectedImage.getAttribute('data-fixed') === 'true') return;

    console.log(`Attribute : ${selectedImage.getAttribute('data-fixed')}`)

    let currentRotation = parseInt(selectedImage.getAttribute("data-rotation")) || 0;
    let newRotation = (currentRotation + degrees) % 360;

    let bbox = selectedImage.getBBox();
    let cx = bbox.x + bbox.width / 2;
    let cy = bbox.y + bbox.height / 2;

    selectedImage.setAttribute("transform", `rotate(${newRotation}, ${cx}, ${cy})`);
    selectedImage.setAttribute("data-rotation", newRotation);
    console.warn(`New rotation : ${newRotation}`)
}

function showContextMenu(event) {
    event.preventDefault();
    selectedImage = event.target;

    if(selectedImage.getAttribute("data-fixed") == "true") return; 
    
    const rect = selectedImage.getBoundingClientRect();

    contextMenu.style.left = `${rect.left}px`;
    contextMenu.style.top = `${rect.top - contextMenu.offsetHeight}px`;
    contextMenu.style.display = 'block';

    event.stopPropagation();
}

document.addEventListener('click', () => {contextMenu.style.display = 'none';});
document.getElementById('rotateLeft').addEventListener('click', () => rotateImage(-90));
document.getElementById('rotateRight').addEventListener('click', () => rotateImage(90));
document.getElementById('rotate180').addEventListener('click', () => rotateImage(180));
document.getElementById('resetRotation').addEventListener('click', () => rotateImage(-parseInt(selectedImage.getAttribute("data-rotation"))));
document.getElementById('flip').addEventListener('click', flipImage);

document.getElementById("confirm").addEventListener("click", () => {
    const nameInput = document.getElementById("nameInput").value;

    if (nameInput.trim() !== "") {
        id = nameInput;

        document.getElementById("connection").style.visibility = "visible";
        document.getElementById("identifier").style.display = "none";

        socket = new WebSocket("ws://localhost:8000");

        initSpecialsTiles('../images/tiles/specials');

        socket.addEventListener("open", () => {
            console.log("Connexion établie !");
            document.getElementById("container").style.display = "flex";
            document.getElementById("nextButton").style.display = "flex";
            document.getElementById("face").style.display = "flex";
            document.getElementById("game").style.display = "none";
            document.getElementById("score").style.display = "flex";
            document.getElementById("specials").style.display = "flex";
            document.getElementById("plays").disabled = false;
            document.getElementById("plays").addEventListener("click", () => {
                socket.send(`${id} PLAYS`);
                document.getElementById("plays").style.display = "none"
                document.getElementById("next").style.display = "flex"
            })
            socket.send(`${id} ENTERS`);
            socket.send(`${id} ELECTS ${id}`);



        });

        socket.addEventListener("message", (event) => {
            addMessage(event.data);
            messageQueue.push(event.data);
            processMessageQueue();
        });

        socket.addEventListener("close", () => {
            console.log("Connexion fermée");
            document.getElementById("connection").textContent = "Aucune partie n'est en cours";
        });

        document.addEventListener("dragstart", (event) => {
            if (event.target.tagName === "IMG") {
                let imgSrc = event.target.getAttribute("src");
                let imgId = event.target.id;
                let dataTile = event.target.getAttribute("data-tile");
                name = document.getElementById(imgId).getAttribute("data-tile");
                
                if(event.target.getAttribute("data-special")) {
                    let dataSpecial = event.target.getAttribute("data-special");
                    let dragData = JSON.stringify({ src: imgSrc, id: imgId, dataTile: dataTile, special: dataSpecial });
                    event.dataTransfer.setData("application/json", dragData);
                } else {
                    let dragData = JSON.stringify({ src: imgSrc, id: imgId, dataTile: dataTile });
                    event.dataTransfer.setData("application/json", dragData);
                }
                
            }
        });

        document.addEventListener("mousemove", (event) => {
            let { x, y } = getMousePosition(event);
            let cell = getCellFromCoordinates(x, y);
        });

        document.addEventListener("drop", (event) => {
            event.preventDefault();
            let { x, y } = getMousePosition(event);
            let cell = getCellFromCoordinates(x, y);

            if (cell != null && !document.getElementById(cell).querySelector('image')) {
                let dragData = event.dataTransfer.getData("application/json");
                let data = JSON.parse(dragData);

                let imgSrc = data.src;
                let imgId = data.id;
                let dataTile = data.dataTile;
                console.log(`Drop dans la case : ${cell} (X: ${x.toFixed(0)}, Y: ${y.toFixed(0)})`);
                console.log(imgSrc);
                let pos = document.getElementById(`${cell}`);

                let group = svg.getElementById(cell);
                let img = document.createElementNS("http://www.w3.org/2000/svg", "image");
                img.setAttribute("href", imgSrc);
                img.setAttribute("id", imgId);
                img.setAttribute("draggable", "true");
                img.setAttribute("data-fixed", false);
                img.setAttribute("data-rotation", '0');
                img.setAttribute("data-flipped",false);
                img.setAttribute('data-tile', dataTile);
                img.setAttribute("width", "250");
                img.setAttribute("height", "250");
                group.appendChild(img);

                if(data.special) {
                    img.setAttribute('data-special', data.special);
                    specials += 1;
                    totalSpecials += 1;
                    moveSpecialTile();
                } else {
                    decrementNumberOfTilesToPlace();
                    console.log(`Decremented values for ${getNumberOfTilesToPlace()}`);
                }
                
                group.appendChild(img);

                img.addEventListener("dblclick", () => {
                    console.log(img.tagName === "IMG")
                    console.log(img.getAttribute("data-fixed") == false)
                    console.log(`Try to resolve : ${img.getAttribute("data-fixed")}`)
                    if (img.getAttribute("data-fixed") == "false") {
                        
                        if(img.getAttribute("data-special")) {
                            const image = document.createElement('img');
                            image.setAttribute('draggable', 'true');
                            image.setAttribute('id', imgId);
                            image.setAttribute('src', imgSrc);
                            image.width = '50';
                            image.height = '50';
                            image.setAttribute("data-special","true");
                            image.setAttribute("data-flipped","false");
                            image.setAttribute("data-fixed","false");
                            image.setAttribute("data-rotation",0);
                            image.setAttribute("data-tile", dataTile);
                            image.classList.add('special-tile');
                            document.querySelector('.specials').appendChild(image);
                            img.remove();
                            delete mouvements[imgId];
                            specials -= 1;
                            totalSpecials -= 1;
                            removeSpecialTile();
                        } else {
                            const divFace = document.querySelector('.face');
                            const imgb = document.createElement('img');
                            imgb.setAttribute('draggable', 'true');
                            imgb.setAttribute('id', imgId);
                            imgb.setAttribute('src', imgSrc);
                            imgb.setAttribute('alt', img.getAttribute("alt")); 
                            imgb.setAttribute('style', 'width: 3%; height: 3%;');
                            imgb.setAttribute('data-tile', dataTile);
                            divFace.appendChild(imgb);
                            
                            img.remove();
                            
                            delete mouvements[imgId];
                            incrementNumberOfTilesToPlace();
                            console.log(`Decremented values for ${getNumberOfTilesToPlace()}`);
                            console.log(Object.keys(mouvements).length);
                            
                            if (numberOfTIlesToPlace > 3) {
                                document.getElementById("next").disabled = true; 
                            }
                        }
                
                        console.log("Image déplacée avec succès !");
                    }
                });

                img.addEventListener('contextmenu', showContextMenu);     

                console.log(`Mouvements for imgId ${imgId} with data-tile ${dataTile} added ...`);
                mouvements[imgId] = [document.getElementById(imgId).getAttribute("data-tile"), null, cell];
                console.log(Object.keys(mouvements).length);

                if (numberOfTIlesToPlace < 4 && round > 0) {
                    document.getElementById("next").disabled = false;
                }

                document.getElementById(imgId).remove();
            }
        });

        document.addEventListener("dragover", (event) => {
            event.preventDefault();
        });

        document.getElementById("next").addEventListener("click", () => {
            if (numberOfTIlesToPlace < 4) {
                for (let cle in mouvements) {
                    if(!actions.includes(mouvements[cle][2])) {
                        console.log(` -> Place tile with rotation ${document.getElementById(cle).getAttribute("data-flipped")} ${rotations[document.getElementById(cle).getAttribute("data-rotation")]}`)
                        let rotation = document.getElementById(cle).getAttribute("data-flipped") == "true" ? `F${rotations[document.getElementById(cle).getAttribute("data-rotation")] == "null" ? "" : rotations[document.getElementById(cle).getAttribute("data-rotation")] }` : rotations[document.getElementById(cle).getAttribute("data-rotation")];
                        document.getElementById(cle).setAttribute("data-fixed", true);
                        console.log(`${id} PLACES ${mouvements[cle][0]} ${rotation} ${mouvements[cle][2]}`);
                        socket.send(`${id} PLACES ${mouvements[cle][0]} ${rotation} ${mouvements[cle][2]}`);
                        actions.push(mouvements[cle][2]);
                    }
                }
                if (numberOfTIlesToPlace - specials >= 0) {
                    socket.send(`${id} YIELDS`);
                }
                specials = 0;
                document.querySelectorAll('.specials img').forEach(img => {
                    img.setAttribute('draggable', 'false');
                });
                document.querySelector('.face').querySelectorAll('img').forEach(img => {
                    img.remove();
                });
                numberOfTIlesToPlace = 0;
                console.log("----------------------------------------------------------------")
                document.getElementById("blameError").style.display = "none";
                document.getElementById("blameError").textContent = "Vous avez recu un blame pour le message suivant : ";
            }
            document.getElementById("next").disabled = true;
        });

    } else {
        alert("Veuillez entrer un nom !");
    }
});

export { getActions, getRound, addRound, getID, decrementNumberOfTilesToPlace, incrementNumberOfTilesToPlace, getNumberOfTilesToPlace, resetSpecialTiles, resetClassicTiles };
