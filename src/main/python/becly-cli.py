import websocket
import thread
import time

WS_URL = "ws://localhost:8080/ws"
BOT_NAME = "Becky"

def on_message(ws, message):
	print "%s: %s\n" % (BOT_NAME, message)
	time.sleep(1)
	chat()

def on_error(ws, error):
	print "Error occurred: %r" % error

def on_close(ws):
	print "Goodbye."

def on_open(ws):
	def run(*args):
		chat()

	thread.start_new_thread(run, ())

def chat():
	message = raw_input("$ ")
	if message != "exit" and message != "bye":
		time.sleep(1)
		ws.send(message)
	else:
		ws.close();


if __name__ == "__main__":
	websocket.enableTrace(False)
	ws = websocket.WebSocketApp(WS_URL, on_message = on_message, on_error = on_error, on_close = on_close)
	ws.on_open = on_open
	ws.run_forever()