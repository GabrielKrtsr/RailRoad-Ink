export default function executeEntersCommand(args) {
    if(args[0] != "admin") {
        let players = args.slice(2);
        players.forEach(player => {
            console.log(`Elects for player ${player}`);
            document.getElementById(player).disabled = false;
            document.getElementById(player).style.backgroundColor = "light-dark";
            console.log(`Set in blue for ${document.getElementById(player)}`);
        });
    }
}