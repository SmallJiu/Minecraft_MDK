@echo off
set y=%date:~0,4%
set month=%date:~5,2%
if month LSS 10 month+=1

set day=%date:~8,2%
if day LSS 10 day+=1

set hour=%time:~0,2%
if hour LSS 10 hour+=1

set min=%time:~3,2%
if min LSS 10 min+=1

set sec=%time:~6,2%
if sec LSS 10 sec+=1

set msg=%y%-%month%-%day%-%hour%-%min%-%sec%
set "msg=%msg: =%"
title %msg%
git add .
git commit -m "%msg%"
echo =============
git log --pretty=oneline
echo =============
