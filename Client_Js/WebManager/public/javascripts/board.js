export default class Board {

    constructor() {
        this.svg = null;
        this.container = document.getElementById("svg-container");
        this.boardState = [];
        this.loadingSvg = true;
        this.tileQueue = [];

        this.blameQueue = [];

        this.network = 0;
        this.longestRail = 0;
        this.longestRoad = 0;
        this.centerCell = 0;
        this.penalties = 0;
        this.total = 0;

        this.rotations = {
            "null" : 0,
            "L" : -90, 
            "R" : 90, 
            "U" : 180,
            "FL" : -90, 
            "FR" : 90, 
            "FU" : 180,
        }

        this.loadSvg();
    }

    async loadSvg() {
        console.log(`Try to load svg`);
        try {
            const response = await fetch("../images/grid.svg");
            const text = await response.text();
            const parser = new DOMParser();
            this.svg = parser.parseFromString(text, "image/svg+xml").documentElement;
        } catch (error) {
            console.error("Erreur lors du chargement du SVG :", error);
        }

        console.log(`Finish to load svg`);
        this.loadingSvg = false;

        while (this.tileQueue.length > 0) {
            const { position, tile, rotation } = this.tileQueue.shift();
            this.addTile(position, tile, rotation);
        }
    }

    addTile(position, tile, rotation) {
        
        if (this.loadingSvg) {
            this.tileQueue.push({ position, tile, rotation });
            return;
        }

        if (!this.svg) return;

        //console.log(`Tile placed in position ${position}`);

        let group = this.svg.getElementById(position);
        if (!group) {
            console.error(`Position ${position} not found in SVG`);
            return;
        }

        let img = document.createElementNS("http://www.w3.org/2000/svg", "image");
        let special = false;

        fetch('/api/svg-files')
            .then(response => response.json())
            .then(fichiers => {
                let img = document.createElementNS("http://www.w3.org/2000/svg", "image");
                let special = false;
                fichiers.forEach(fichier => {
                    if(fichier == `${tile}.svg`) {
                        special = true;
                    }
                });
                if(special == true) {
                    img.setAttribute("href", `/images/tiles/specials/${tile}.svg`);
                } else {
                    img.setAttribute("href", `/images/tiles/${tile}.svg`);
                }
                img.setAttribute("width", "250");
                img.setAttribute("height", "250");
                img.setAttribute("data-rotation", '0');
                img.setAttribute("data-flipped",false);
        
                if(rotation.startsWith('F')) {
                    this.flipImage(img);
                }
        
                if(rotation != "null" && rotation != "F") {
                    this.rotateImage(this.rotations[rotation],img);
                }
        
                if(this.blameQueue.includes(position)) {
                    console.error(`DONT'T PLACE ${position} BECAUSE OF BLAME !!!`)
                    onsole.log(`Array of blames after: ${this.blameQueue}`)
                    this.blameQueue.filter(cell => cell != position)
                    console.log(`Array of blames before: ${this.blameQueue}`)
                    return
                }; 

                group.appendChild(img);


                this.boardState.push({ position, tile });
                //console.log("Tile has been added correctly!");
                this.showBoard();

            })
            .catch(error => {
                console.error('Erreur lors du chargement des images SVG:', error);
            });

    }

    showBoard() {
        //console.error(`Check cond : Cont ${this.container} & SVG ${this.svg}`);
        if (this.container && this.svg) {
            //console.log("Enters into cond");
            this.container.innerHTML = "";
            this.container.appendChild(this.svg.cloneNode(true));
            document.getElementById("score-N").textContent = this.network
            document.getElementById("score-H").textContent = this.longestRoad
            document.getElementById("score-R").textContent = this.longestRail
            document.getElementById("score-C").textContent = this.centerCell
            document.getElementById("score-X").textContent = this.penalties
            document.getElementById("score-TOTAL").textContent = this.total
            console.log(`Update score : ${this.total}`)
        }
    }

    flipImage(img) {
        let flipped = img.getAttribute("data-flipped") === "true";
        let scaleX = flipped ? 1 : -1;
        let cx = 125;
        let cy = 125;
        img.setAttribute("transform", `scale(${scaleX},1) translate(${scaleX === -1 ? -250 : 0},0)`);
        img.setAttribute("data-flipped", flipped ? "false" : "true");
        console.warn(`New flip : ${img.getAttribute("data-flipped")}`)
    }
    
    
    rotateImage(degrees, img) {
        let currentRotation = parseInt(img.getAttribute("data-rotation")) || 0;
        let newRotation = (currentRotation + degrees) % 360;
        let cx = 125;
        let cy = 125;
        img.setAttribute("transform", `rotate(${newRotation}, ${cx}, ${cy})`);
        img.setAttribute("data-rotation", newRotation);
        console.warn(`New rotation : ${newRotation}`)
    }

    addBlame(cell) {
        this.blameQueue.push(cell);
        console.log(`Add blame for cell ${cell}`);
    }
     
}