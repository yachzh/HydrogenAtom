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

