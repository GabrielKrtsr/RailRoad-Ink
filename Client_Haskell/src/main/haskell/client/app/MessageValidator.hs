module MessageValidator where

import Data.Text (Text)
import qualified Data.Text as T

validTiles :: [T.Text]
validTiles = map T.pack ["H", "Hj", "Hc", "S", "Sc", "HH", "SH", "SHR", "R", "Rj", "Rc", "HR", "RR", "SR", "SS"]

validOrientations :: [T.Text]
validOrientations = map T.pack ["S", "L", "R", "U", "F", "FL", "FR", "FU"]

validScoreTypes :: [T.Text]
validScoreTypes = map T.pack ["N", "H", "R", "C", "X", "E", "TOTAL"]

validateId :: T.Text -> Either String ()
validateId idTxt
    | T.all (`elem` ['a'..'z'] ++ ['A'..'Z'] ++ ['0'..'9']) idTxt = Right ()
    | otherwise = Left "ID invalide"

validateTile :: T.Text -> Either String ()
validateTile tile
    | tile `elem` validTiles = Right ()
    | otherwise = Left "Tuile invalide"

validateOrientation :: T.Text -> Either String ()
validateOrientation orientation
    | orientation `elem` validOrientations = Right ()
    | otherwise = Left "Orientation invalide"

validateInt :: T.Text -> Either String ()
validateInt txt
    | T.all (`elem` ['0'..'9']) txt = Right ()
    | otherwise = Left "Rang invalide"

validateScoreType :: T.Text -> Either String ()
validateScoreType scoreType
    | scoreType `elem` validScoreTypes = Right ()
    | otherwise = Left "Type de score invalide"

validateMessage :: T.Text -> Either String T.Text
validateMessage msg =
    case T.words msg of
        (idTxt:cmd:args) -> case cmd of
            _ | cmd == T.pack "ENTERS"  -> validateId idTxt >> Right msg
              | cmd == T.pack "LEAVES"  -> validateId idTxt >> Right msg
              | cmd == T.pack "THROWS"  -> case args of
                    [tile] -> validateId idTxt >> validateTile tile >> Right msg
                    _      -> Left "Format invalide"
              | cmd == T.pack "PLACES"  -> case args of
                    [tile, orientation] -> validateId idTxt >> validateTile tile >> validateOrientation orientation >> Right msg
                    _                   -> Left "Format invalide"
              | cmd == T.pack "SCORES"  -> case args of
                    [rang, scoreType, score] -> validateId idTxt >> validateInt rang >> validateScoreType scoreType >> validateInt score >> Right msg
                    _                        -> Left "Format invalide"
              | cmd == T.pack "BLAMES"  -> case args of
                    [rang] -> validateId idTxt >> validateInt rang >> Right msg
                    _      -> Left "Format invalide"
              | cmd == T.pack "GRANTS"  -> case args of
                    (id2:message) -> validateId idTxt >> validateId id2 >> Right msg
                    _             -> Left "Format invalide"
              | cmd == T.pack "AGREES"  -> validateId idTxt >> Right msg
              | cmd == T.pack "ELECTS"  -> validateId idTxt >> Right msg
              | cmd == T.pack "YIELDS"  -> validateId idTxt >> Right msg
              | otherwise               -> Left "Commande invalide"
        _ -> Left "Message invalide"


