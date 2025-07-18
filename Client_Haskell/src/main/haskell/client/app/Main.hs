module Main where
import Network.WebSockets (runClient)
import Client (clientLoop)

serverHost :: String
serverHost = "localhost"
serverPort :: Int
serverPort = 3000

main :: IO ()
main = runClient serverHost serverPort "/" clientLoop

