module TestAutomaton where

import Test.HUnit
import qualified Data.Text as T
import Automaton (Automaton(..), initialAutomaton, handleMessage)

testInitialEnter :: Test
testInitialEnter = TestCase $ assertEqual "Initial state ENTERS" (Right (Active, T.pack "player1 ENTERS")) (handleMessage Waiting (T.pack "player1 ENTERS"))

testDoubleEnter :: Test
testDoubleEnter = TestCase $ assertEqual "Double ENTERS not allowed" (Left "Erreur : Vous avez déjà envoyé ENTERS.") (handleMessage Active (T.pack "player1 ENTERS"))

testLeaveAfterEnter :: Test
testLeaveAfterEnter = TestCase $ assertEqual "LEAVES after ENTERS" (Right (LeftGame, T.pack "player1 LEAVES")) (handleMessage Active (T.pack "player1 LEAVES"))

testMessageAfterLeave :: Test
testMessageAfterLeave = TestCase $ assertEqual "No messages after LEAVES" (Left "Erreur : Vous devez être actif pour envoyer ce message.") (handleMessage LeftGame (T.pack "player1 THROWS H"))

testMessageBeforeEnter :: Test
testMessageBeforeEnter = TestCase $ assertEqual "No messages before ENTERS" (Left "Erreur : Vous devez être actif pour envoyer ce message.") (handleMessage Waiting (T.pack "player1 THROWS H"))

testJudgeCanScore :: Test
testJudgeCanScore = TestCase $ assertEqual "Judge can send SCORES" (Right (Judge, T.pack "player1 SCORES 1 N 10")) (handleMessage Judge (T.pack "player1 SCORES 1 N 10"))

testNonJudgeCannotScore :: Test
testNonJudgeCannotScore = TestCase $ assertEqual "Non-judge cannot send SCORES" (Left "Erreur : Seul un juge peut envoyer ce message.") (handleMessage Active (T.pack "player1 SCORES 1 N 10"))

testOrganizerCanElect :: Test
testOrganizerCanElect = TestCase $ assertEqual "Organizer can send ELECTS" (Right (Judge, T.pack "player1 ELECTS player2")) (handleMessage Organizer (T.pack "player1 ELECTS player2"))

testNonOrganizerCannotElect :: Test
testNonOrganizerCannotElect = TestCase $ assertEqual "Non-organizer cannot send ELECTS" (Left "Erreur : Seul un organisateur peut nommer un juge.") (handleMessage Active (T.pack "player1 ELECTS player2"))

tests :: Test
tests = TestList [testInitialEnter, testDoubleEnter, testLeaveAfterEnter, testMessageAfterLeave, testMessageBeforeEnter, testJudgeCanScore, testNonJudgeCannotScore, testOrganizerCanElect, testNonOrganizerCannotElect]

main :: IO Counts
main = runTestTT tests
