import {addRound, incrementNumberOfTilesToPlace, getNumberOfTilesToPlace, resetSpecialTiles, resetClassicTiles} from '../jeuScript.js'
import {clearMessages} from '../messages.js'

export default function executeThrowsCommand(args){

    //clearMessages();

    resetSpecialTiles();
    resetClassicTiles();

    document.getElementById("blameError").style.display = "none";
    document.getElementById("blameError").textContent = "Vous avez recu un blame pour le message suivant : ";

    const divFace = document.querySelector('.face');

    divFace.querySelectorAll('img').forEach(img => {
        img.remove();
    });

    let round = addRound();


    for (let i = 2; i < 6; i++) {
        incrementNumberOfTilesToPlace();
        console.log(`Incremented values for ${getNumberOfTilesToPlace()}`);
        const img = document.createElement('img');
        img.setAttribute('draggable', 'true');
        img.setAttribute('id', 'f' + (i - 1) + '' + round);
        img.setAttribute('src', `../images/tiles/${args[i]}.svg`);
        img.setAttribute('alt', 'Image ' + (i - 1)); 
        img.setAttribute('style', 'width: 3%; height: 3%;');
        img.setAttribute('data-tile', args[i]);
        img.setAttribute('data-fixed', false);
        
        // Ajouter l'image à la div 'face'
        divFace.appendChild(img);
    }

    if (getNumberOfTilesToPlace() > 0) {
        document.getElementById("next").disabled = true;
    }

    //document.getElementById("f1").src = `../images/tiles/${args[2]}.svg`;
    //document.getElementById("f2").src = `../images/tiles/${args[3]}.svg`;
    //document.getElementById("f3").src = `../images/tiles/${args[4]}.svg`;
    //document.getElementById("f4").src = `../images/tiles/${args[5]}.svg`;
    //document.getElementById("f5").src = `../images/tiles/${args[6]}.svg`;
    //document.getElementById("f1").setAttribute("data-tile", args[2]);
    //document.getElementById("f2").setAttribute("data-tile", args[3]);
    //document.getElementById("f3").setAttribute("data-tile", args[4]);
    //document.getElementById("f4").setAttribute("data-tile", args[5]);
    //document.getElementById("f5").setAttribute("data-tile", args[6]);
}