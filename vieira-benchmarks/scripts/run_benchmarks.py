import subprocess
import os
import datetime
from pathlib import Path

BENCHMARK_CLASSES = [
    "io.vieira.benchmarks.VieiraBenchmarks",
    "io.vieira.benchmarks.VieiraConcurrentBenchmarks"
]

CONFIGURATIONS = [
    {
        "name": "throughput",
        "args": ["-bm", "thrpt", "-f", "1", "-wi", "5", "-i", "5", "-t", "1"]
    },
    {
        "name": "latency",
        "args": ["-bm", "avgt", "-f", "1", "-wi", "5", "-i", "5", "-t", "1"]
    },
    {
        "name": "concurrent_throughput",
        "args": ["-bm", "thrpt", "-f", "1", "-wi", "5", "-i", "5", "-t", "4"]
    }
]

def run_benchmark(benchmark_class, config):
    print(f"\nRunning {benchmark_class} with {config['name']} configuration...")
    gradle_cmd = "gradlew.bat" if os.name == "nt" else "./gradlew"
    args = [gradle_cmd, ":vieira-benchmarks:jmh", "-PjmhArgs=" + " ".join(config['args'] + [benchmark_class])]
    
    try:
        result = subprocess.run(args, capture_output=True, text=True, check=True)
        return result.stdout
    except subprocess.CalledProcessError as e:
        print(f"Error running benchmark: {e}")
        return ""

def main():
    timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
    output_file = Path("benchmark_reports") / f"benchmark_results_{timestamp}.txt"
    output_file.parent.mkdir(exist_ok=True)
    
    with open(output_file, "w") as f:
        f.write(f"Benchmark Results - {timestamp}\n\n")
        
        for config in CONFIGURATIONS:
            f.write(f"\n=== {config['name'].upper()} CONFIGURATION ===\n\n")
            
            for benchmark_class in BENCHMARK_CLASSES:
                f.write(f"\n--- {benchmark_class} ---\n")
                output = run_benchmark(benchmark_class, config)
                f.write(output)
                f.write("\n")
    
    print(f"\nBenchmark results saved to: {output_file}")

if __name__ == "__main__":
    main() 