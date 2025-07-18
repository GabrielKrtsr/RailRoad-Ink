module TestMessageValidator where

import Test.HUnit
import qualified Data.Text as T
import MessageValidator (validateMessage)

testValidEnter :: Test
testValidEnter = TestCase $ assertEqual "Valid ENTERS message" (Right (T.pack "player1 ENTERS")) (validateMessage (T.pack "player1 ENTERS"))

testValidLeaves :: Test
testValidLeaves = TestCase $ assertEqual "Valid LEAVES message" (Right (T.pack "player1 LEAVES")) (validateMessage (T.pack "player1 LEAVES"))

testValidThrows :: Test
testValidThrows = TestCase $ assertEqual "Valid THROWS message" (Right (T.pack "player1 THROWS H")) (validateMessage (T.pack "player1 THROWS H"))

testInvalidThrows :: Test
testInvalidThrows = TestCase $ assertEqual "Invalid THROWS message" (Left "Tuile invalide") (validateMessage (T.pack "player1 THROWS XYZ"))

testValidPlaces :: Test
testValidPlaces = TestCase $ assertEqual "Valid PLACES message" (Right (T.pack "player1 PLACES H S")) (validateMessage (T.pack "player1 PLACES H S"))

testInvalidPlaces :: Test
testInvalidPlaces = TestCase $ assertEqual "Invalid PLACES message" (Left "Orientation invalide") (validateMessage (T.pack "player1 PLACES H XYZ"))

testValidScores :: Test
testValidScores = TestCase $ assertEqual "Valid SCORES message" (Right (T.pack "player1 SCORES 1 N 10")) (validateMessage (T.pack "player1 SCORES 1 N 10"))

testInvalidScores :: Test
testInvalidScores = TestCase $ assertEqual "Invalid SCORES message" (Left "Type de score invalide") (validateMessage (T.pack "player1 SCORES 1 XYZ 10"))

testValidBlames :: Test
testValidBlames = TestCase $ assertEqual "Valid BLAMES message" (Right (T.pack "player1 BLAMES 1")) (validateMessage (T.pack "player1 BLAMES 1"))

testInvalidMessage :: Test
testInvalidMessage = TestCase $ assertEqual "Invalid message" (Left "Commande invalide") (validateMessage (T.pack "player1 UNKNOWN"))

tests :: Test
tests = TestList [testValidEnter, testValidLeaves, testValidThrows, testInvalidThrows,
                  testValidPlaces, testInvalidPlaces, testValidScores, testInvalidScores,
                  testValidBlames, testInvalidMessage]

main :: IO Counts
main = runTestTT tests
