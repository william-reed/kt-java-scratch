import zlib
import base64
import sys
import datetime

# how long and how effective is string compression?

input = "".join(sys.stdin.readlines())

print('intial len ' + str(len(input)))

start_time_ms = datetime.datetime.now().timestamp() * 1000
compressed = zlib.compress(input.encode(), 9)
print(type(compressed))
compressed = base64.b64encode(compressed)
print(type(compressed))
print(compressed.decode('ascii'))

print('final len ' + str(len(compressed.decode('ascii'))))
end_time_ms = datetime.datetime.now().timestamp() * 1000
print('time: ' + str(end_time_ms - start_time_ms) + ' ms')

