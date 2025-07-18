module Automaton (Automaton(..), initialAutomaton, handleMessage) where

import Data.Text (Text)
import qualified Data.Text as T
import MessageValidator (validateMessage, validateId)

data Automaton = Waiting | Passive | Active | LeftGame | Organizer | Judge deriving (Eq, Show)

-- hashmap automate transition action

initialAutomaton :: Automaton
initialAutomaton = Waiting

handleMessage :: Automaton -> Text -> Either String (Automaton, Text)
handleMessage state msg =
    case T.words msg of
        (idTxt:cmd:args) -> case cmd of
            _ | cmd == T.pack "ENTERS" ->
                if state == Active || state == Organizer || state == Judge
                then Left "Erreur : Vous avez déjà envoyé ENTERS."
                else validateMessage msg >> Right (Active, msg)
              | cmd == T.pack "LEAVES" ->
                if state == Active || state == Organizer || state == Judge
                then validateMessage msg >> Right (LeftGame, msg)
                else Left "Erreur : Vous devez être actif pour envoyer LEAVES."
              | cmd == T.pack "THROWS" || cmd == T.pack "PLACES" || cmd == T.pack "YIELDS" ->
                if state == Active
                then validateMessage msg >> Right (Active, msg)
                else Left "Erreur : Vous devez être actif pour envoyer ce message."
              | cmd == T.pack "SCORES" || cmd == T.pack "BLAMES" ->
                if state == Judge
                then validateMessage msg >> Right (Judge, msg)
                else Left "Erreur : Seul un juge peut envoyer ce message."
              | cmd == T.pack "GRANTS" ->
                if state == Organizer
                then validateMessage msg >> Right (Organizer, msg)
                else Left "Erreur : Seul un organisateur peut envoyer ce message."
              | cmd == T.pack "AGREES" ->
                if state == Organizer
                then validateMessage msg >> Right (Organizer, msg)
                else Left "Erreur : Seul un organisateur peut envoyer ce message."
              | cmd == T.pack "ELECTS" ->
                if state == Organizer
                then validateMessage msg >> Right (Judge, msg)
                else Left "Erreur : Seul un organisateur peut nommer un juge."
              | state == LeftGame -> Left "Erreur : Vous avez quitté la partie et ne pouvez plus envoyer de messages."
              | state == Waiting -> Left "Erreur : Vous devez envoyer ENTERS pour devenir actif."
              | otherwise -> Left "Erreur : Commande invalide."
        _ -> Left "Message invalide"
