module Main where

import Test.HUnit
import TestMessageValidator
import TestAutomaton

main :: IO ()
main = do
    putStrLn "Running MessageValidator tests..."
    r1 <- runTestTT TestMessageValidator.tests
    putStrLn "Running Automaton tests..."
    r2 <- runTestTT TestAutomaton.tests
    _ <- sequence [return r1, return r2]  -- Exécuter les tests proprement
    return ()
