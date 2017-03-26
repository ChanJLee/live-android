#!/bin/bash

branch=$1
message=$2

if [[ -z $branch ]]; then
  #statements
  branch="master"
fi

if [[ -z $message ]]; then
  #statements
  message="default message"
fi

echo "I love wenYu Lee."

git pull origin $branch
echo "pull code from branch $branch"

git add .
git commit -m "$message"
echo "commit message is $message"
git push origin $branch