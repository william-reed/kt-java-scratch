import zlib
import base64
import sys
import datetime

# how long and how effective is string compression?

input = "".join(sys.stdin.readlines())

print('intial len ' + str(len(input)))

start_time_ms = datetime.datetime.now().timestamp() * 1000
compressed = base64.b64encode(zlib.compress(input.encode(), 9))
end_time_ms = datetime.datetime.now().timestamp() * 1000

print('final len ' + str(len(compressed)))
print('time: ' + str(end_time_ms - start_time_ms) + ' ms')

