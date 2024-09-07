VERSION = "2.0.0"

local config = import("micro/config")
local shell = import("micro/shell")
local filepath = import("path/filepath")
local micro = import("micro")

local fmtCommands = {}
fmtCommands["python"] = "yapf -i"
fmtCommands["c"]      = "clang-format -i"
fmtCommands["c++"]    = "clang-format -i"
fmtCommands["csharp"] = "clang-format -i"
fmtCommands["java"] = "clang-format -i"
fmtCommands["racket"] = "raco fmt --width 80 --max-blank-lines 2 -i"
fmtCommands["javascript"] = "prettier --write --loglevel silent"
fmtCommands["rust"] = "rustfmt +nightly"

config.RegisterCommonOption("autofmt", "fmt-onsave", true)

function init()
    config.MakeCommand("fmt", fmtCmd, config.NoComplete)
    config.AddRuntimeFile("fmt", config.RTHelp, "help/autofmt.md")
end

function onSave(bp)
    tryFmt(bp)
end

function tryFmt(bp)
    if bp.Buf.Settings["autofmt.fmt-onsave"] then
        if fmtCommands[bp.Buf:FileType()] ~= nil then
            doFmt(bp, fmtCommands[bp.Buf:FileType()])
        end
    end
end

function fmtCmd(bp)
    if fmtCommands[bp.Buf:FileType()] ~= nil then
        doFmt(bp, fmtCommands[bp.Buf:FileType()])
    end
end

function doFmt(bp, fmtCmd)
    bp:Save()
    local dirPath, _ = filepath.Split(bp.Buf.AbsPath)
    if string.find(dirPath, "/") ~= nil then -- unix
        local res, err = os.execute(fmtCmd .. " \"" .. bp.Buf.AbsPath .. "\"")
        if err ~= nil then
            micro.InfoBar():Error(err)
            return
        end
        bp.Buf:ReOpen()
        return
    end
    -- windows
    local path = string.gsub(bp.Buf.AbsPath, "\\", "\\\\")
    path = string.gsub(path, " ", "\\ ")
    local res, err = os.execute("echo " .. fmtCmd .. " " .. path .. " | sh")
    if err ~= nil then
        micro.InfoBar():Error(err)
        return
    end
    bp.Buf:ReOpen()
end