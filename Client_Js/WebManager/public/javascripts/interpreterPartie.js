class Interpreter {

    constructor() {
        this.commands = {
            "PLACES": "./actions/places.js",
            "ENTERS": "./actions/enters.js",
            "ELECTS": "./actions/elects.js",
            "ENTERS": "./actions/enters.js",
            "LEAVES": "./actions/leaves.js",
            "SCORES": "./actions/scores.js",
            "BLAMES": "./actions/blames.js"
        };
    }

    async interpret(message) {
        let words = message.split(" ");
        if (words.length < 2) {
            console.error("Message invalide : doit contenir au moins deux mots.");
            return;
        }

        let commandName = words[1];
        if (this.commands[commandName]) {
            try {
                let module = await import(this.commands[commandName]);
                if (module.default) {
                    module.default(words);
                } else {
                    console.error(`La commande '${commandName}' ne possède pas d'export 'default'.`);
                }
            } catch (error) {
                console.error(`Erreur lors du chargement de la commande '${commandName}':`, error);
            }
        } else {
            console.error(`Commande inconnue: ${commandName}`);
        }
    }
}

export default new Interpreter();
