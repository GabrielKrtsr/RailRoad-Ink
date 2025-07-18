module Client (clientLoop) where

import Control.Concurrent (forkIO)
import Control.Exception (finally)
import Control.Monad (forever)
import Network.WebSockets (ClientApp, receiveData, sendTextData, sendClose)
import qualified Data.Text as T
import qualified Data.Text.IO as TIO
import MessageValidator (validateMessage)
import Automaton (Automaton, initialAutomaton, handleMessage)

clientLoop :: ClientApp ()
clientLoop connection = do
    putStrLn "Connecté au réflecteur. Entrez un message :"
    _ <- forkIO (receiveLoop connection)
    finally (sendLoop initialAutomaton) (sendClose connection (T.pack "Déconnexion"))
  where
    --automate
    sendLoop :: Automaton -> IO ()
    sendLoop state = do
        msg <- TIO.getLine
        case handleMessage state msg of
            Left err -> putStrLn err >> sendLoop state
            Right (newState, validMsg) -> do
                sendTextData connection validMsg
                sendLoop newState

    receiveLoop conn = forever $ do
        response <- receiveData conn
        TIO.putStrLn $ T.append (T.pack "Message reçu : ") response



