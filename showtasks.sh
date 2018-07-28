#!/usr/bin/env bash

GET_TASKS="http://localhost:8080/crud/v1/task/getTasks"
TIME_TO_SLEEP=5

runcrud() {
    echo "Executing runcrud.sh ..."
    bash runcrud.sh
}

open_browser_with_url() {
    echo "Wait $TIME_TO_SLEEP seconds for tomcat and open the list with tasks in google chrome ..."
    sleep $TIME_TO_SLEEP
    google-chrome $GET_TASKS
}

end() {
  echo "showtasks.sh is finished"
}

if runcrud; then
    echo "runcrud.sh successfully executed."
    open_browser_with_url
    end
else
    "There were some problems!"
fi
