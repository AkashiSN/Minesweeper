#!/bin/sh

cd out/production/Minesweeper/

java --module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml -classpath .:../../../lib/minimal-json-0.9.5.jar minesweeper.Main

