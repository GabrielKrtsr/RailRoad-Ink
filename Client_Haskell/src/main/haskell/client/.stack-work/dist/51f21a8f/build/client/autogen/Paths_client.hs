{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
{-# OPTIONS_GHC -w #-}
module Paths_client (
    version,
    getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where


import qualified Control.Exception as Exception
import qualified Data.List as List
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude


#if defined(VERSION_base)

#if MIN_VERSION_base(4,0,0)
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#else
catchIO :: IO a -> (Exception.Exception -> IO a) -> IO a
#endif

#else
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#endif
catchIO = Exception.catch

version :: Version
version = Version [0,1,0,0] []

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir `joinFileName` name)

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath



bindir, libdir, dynlibdir, datadir, libexecdir, sysconfdir :: FilePath
bindir     = "C:\\Users\\yassi\\OneDrive\\Desktop\\client-haskell\\.stack-work\\install\\bb35f2b4\\bin"
libdir     = "C:\\Users\\yassi\\OneDrive\\Desktop\\client-haskell\\.stack-work\\install\\bb35f2b4\\lib\\x86_64-windows-ghc-9.4.6\\client-0.1.0.0-85Q2pFyFOOU8SpPD87SBI2-client"
dynlibdir  = "C:\\Users\\yassi\\OneDrive\\Desktop\\client-haskell\\.stack-work\\install\\bb35f2b4\\lib\\x86_64-windows-ghc-9.4.6"
datadir    = "C:\\Users\\yassi\\OneDrive\\Desktop\\client-haskell\\.stack-work\\install\\bb35f2b4\\share\\x86_64-windows-ghc-9.4.6\\client-0.1.0.0"
libexecdir = "C:\\Users\\yassi\\OneDrive\\Desktop\\client-haskell\\.stack-work\\install\\bb35f2b4\\libexec\\x86_64-windows-ghc-9.4.6\\client-0.1.0.0"
sysconfdir = "C:\\Users\\yassi\\OneDrive\\Desktop\\client-haskell\\.stack-work\\install\\bb35f2b4\\etc"

getBinDir     = catchIO (getEnv "client_bindir")     (\_ -> return bindir)
getLibDir     = catchIO (getEnv "client_libdir")     (\_ -> return libdir)
getDynLibDir  = catchIO (getEnv "client_dynlibdir")  (\_ -> return dynlibdir)
getDataDir    = catchIO (getEnv "client_datadir")    (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "client_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "client_sysconfdir") (\_ -> return sysconfdir)




joinFileName :: String -> String -> FilePath
joinFileName ""  fname = fname
joinFileName "." fname = fname
joinFileName dir ""    = dir
joinFileName dir fname
  | isPathSeparator (List.last dir) = dir ++ fname
  | otherwise                       = dir ++ pathSeparator : fname

pathSeparator :: Char
pathSeparator = '\\'

isPathSeparator :: Char -> Bool
isPathSeparator c = c == '/' || c == '\\'
