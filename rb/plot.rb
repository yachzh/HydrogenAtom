# Read data from the generated wave_function.dat file
data_file = 'wave_function.dat'

# Check if the data file exists
unless File.exist?(data_file)
  puts "Data file #{data_file} does not exist."
  exit
end

# Open a pipe to Gnuplot
IO.popen('gnuplot', 'w') do |io|
  # Send Gnuplot commands
  io.puts "set terminal png"
  io.puts "set output 'wave_function.png'"
  io.puts "set title 'Wave Function'"
  io.puts "set xlabel 'Position'"
  io.puts "set ylabel 'Wave Function'"
  
  # Use the data file directly
  io.puts "plot '#{data_file}' using 1:2 with linespoints title 'Wave Function'"
end

puts 'Wave function plot saved as wave_function.png'
