#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import sys


if __name__ == '__main__':
    files = sys.argv[1:]
    if not files:
        sys.stderr.write("""pinyin2java.py FILES

Transforms the Uni2Pinyin file to Java properties files.
""")
        sys.exit(1)

    with open("pinyin.properties", 'wb') as file_output:
        
        def write(line):
            file_output.write(line.encode("utf-8"))
            file_output.write("\n")
        
        for filename in files:
            with open(filename, 'rb') as file_input:
                for line_number, line in enumerate(file_input):
                    try:
                        line = line.strip().decode("utf-8")
                        if not line or line.startswith("#"):
                            write(line)
                            continue
                    
                        parts = line.split()
                        char = unichr(int(parts[0], 16))
                        write("{}={}".format(char, ",".join(parts[1:])))
                    except:
                        sys.stderr.write("Error on line {}:\n".format(line_number))
                        raise
