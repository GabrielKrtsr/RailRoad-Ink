exec : 
	cargo clean && cargo build

exec-release : 
	cargo clean && cargo build --release

run : exec-release
	./target/release/RReflector
	
test : exec
	./target/debug/RReflector

