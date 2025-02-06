require 'java'
require '../target/HydrogenAtom-1.0-SNAPSHOT-jar-with-dependencies.jar'

java_import 'zr.yach.HydrogenAtom'

atom = HydrogenAtom.new(0, 6.0, 5000)
atom.solve

eigenvalue = atom.getEigenvalue
puts "Ground state energy: #{eigenvalue} Hartree"
position = atom.getPosition
wave = atom.getEigenstate

# Write data to file
File.open('wave_function.dat', 'w') do |file|
  position.each_with_index do |pos, index|
    file.puts "#{pos} #{wave[index]}"
  end
end
puts 'Ground state wave function saved to wave_function.dat'

# plot data using gnuplot
data = position.each_with_index.map { |pos, index| "#{pos} #{wave[index]}" }.join("\n")

# Open a pipe to Gnuplot
IO.popen('gnuplot', 'w') do |io|
  # Send Gnuplot commands
  io.puts "set terminal png"
  io.puts "set output 'wave_function.png'"
  io.puts "set title 'Wave Function'"
  io.puts "set xlabel 'r (Bohr)'"
  io.puts "set ylabel 'Wave Function'"
  
  # Use the data directly
  io.puts "plot '-' using 1:2 with linespoints title 'Wave Function'"
  
  # Send the data to Gnuplot
  io.puts data
  
  # End the data input
  io.puts "e"  # 'e' signifies the end of data input
end

puts 'Wave function plot saved as wave_function.png'


