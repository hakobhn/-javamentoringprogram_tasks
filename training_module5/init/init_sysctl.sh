#!/bin/sh

if [[ "$(sysctl -n vm.max_map_count)" -lt 524288 ]]; then
      sysctl -w vm.max_map_count=524288
    fi
    if [[ "$(sysctl -n fs.file-max)" -lt 131072 ]]; then
      sysctl -w fs.file-max=131072
    fi
    if [[ "$(ulimit -n)" != "unlimited" ]]; then
      if [[ "$(ulimit -n)" -lt 131072 ]]; then
        echo "ulimit -n 131072"
        ulimit -n 131072
      fi
    fi
    if [[ "$(ulimit -u)" != "unlimited" ]]; then
      if [[ "$(ulimit -u)" -lt 8192 ]]; then
        echo "ulimit -u 8192"
        ulimit -u 8192
      fi
    fi