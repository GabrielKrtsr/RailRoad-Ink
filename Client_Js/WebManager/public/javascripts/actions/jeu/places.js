import {decrementNumberOfTilesToPlace, getID} from '../../jeuScript.js'

let i = 0;
let rotations = {
    "null" : 0,
    "L" : -90, 
    "R" : 90, 
    "U" : 180,
    "FL" : -90, 
    "FR" : 90, 
    "FU" : 180,
}

function flipImage(img) {

    let flipped = img.getAttribute("data-flipped") === "true";
    let scaleX = flipped ? 1 : -1;
    let bbox = img.getBBox();
    let cx = bbox.x + bbox.width / 2;

    img.setAttribute("transform", `scale(${scaleX}, 1) translate(${scaleX === -1 ? -bbox.width - 2 * bbox.x : 0}, 0)`);
    img.setAttribute("data-flipped", flipped ? "false" : "true");
}

function rotateImage(degrees, img) {

    let currentRotation = parseInt(img.getAttribute("data-rotation")) || 0;
    let newRotation = (currentRotation + degrees) % 360;

    let bbox = img.getBBox();
    let cx = bbox.x + bbox.width / 2;
    let cy = bbox.y + bbox.height / 2;

    img.setAttribute("transform", `rotate(${newRotation}, ${cx}, ${cy})`);
    img.setAttribute("data-rotation", newRotation);
}

export default function executePlacesCommand(args) {
    let tile = args[2];
    let rotation = args[3];
    let position = args[4];

    document.querySelectorAll('.specials img').forEach(img => {
        img.setAttribute('draggable', 'false');
    });

    if(!document.getElementById(position).querySelector("image") && args[0] == getID()) {

        console.log(`Placing image in cell ${position} for tile ${tile}`);

        if(document.querySelector(`.specials img#${tile}`) == null) {
            console.log("Classic face");
            const divFace = document.querySelector('.face');
            let image = null;

            divFace.querySelectorAll('img').forEach(img => {
                console.log(`Data tile attr : ${img.getAttribute("data-tile")}`)
                if(img.getAttribute("data-tile") == tile) {
                    image = img;
                    return;
                }
            });

            let group = document.getElementById("svgD").getElementById(position);
            let img = document.createElementNS("http://www.w3.org/2000/svg", "image");
            i += 1;

            img.setAttribute("href", `../images/tiles/${tile}.svg`);
            img.setAttribute("id", image.getAttribute("id"));
            img.setAttribute("width", "250");
            img.setAttribute("height", "250");
            img.setAttribute("draggable", "true");
            img.setAttribute("data-fixed", true);
            img.setAttribute("data-rotation", '0');
            img.setAttribute("data-flipped",false);
            img.setAttribute('data-tile', image.getAttribute("data-tile"));

            group.appendChild(img);
            image.remove();

            if(rotation.startsWith('F')) {
                flipImage(img);
            }

            if(rotation != "null" && rotation != "F") {
                rotateImage(rotations[rotation],img);
            }

            decrementNumberOfTilesToPlace();
        } else {
            const image = document.createElementNS("http://www.w3.org/2000/svg", "image");
            image.setAttribute('href', `../images/tiles/specials/${tile}.svg`);
            image.setAttribute('id', tile);
            image.setAttribute('width', '250');
            image.setAttribute('height', '250');
            image.setAttribute('draggable', 'false');
            image.setAttribute("data-special","true");
            image.setAttribute("data-flipped","false");
            image.setAttribute("data-fixed","false");
            image.setAttribute("data-rotation",0);
            image.setAttribute("data-tile", tile);
            image.classList.add('special-tile');
            document.getElementById("svgD").getElementById(position).appendChild(image);
            document.querySelector(`.specials img#${tile}`).remove();
            console.log("Special tile placed");
            console.log(document.getElementById("svgD").getElementById(position));
        }

    } else {
        console.log("Can't place tile on another tile");
    }
    
}
