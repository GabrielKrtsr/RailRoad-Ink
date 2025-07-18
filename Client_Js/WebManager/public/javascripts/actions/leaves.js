export default function executeEntersCommand(args) {
    document.getElementById(args[0]).disabled = true;
    document.getElementById(args[0]).style.backgroundColor = "lightgrey";
}