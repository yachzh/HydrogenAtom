# import os
import jpype
from pywrite import write_dat
import matplotlib.pyplot as plt

# Start the JVM
# dep_jarpath = os.environ['HOME'] + \
#     '/.m2/repository/org/jblas/jblas/1.2.4/jblas-1.2.4.jar'
# jpype.startJVM(
#    classpath=['../target/HydrogenAtom-1.0-SNAPSHOT.jar', dep_jarpath])

jpype.startJVM(
    classpath=['../target/HydrogenAtom-1.0-SNAPSHOT-jar-with-dependencies.jar'])

# Import the Java class
HydrogenAtom = jpype.JClass('zr.yach.HydrogenAtom')

atom = HydrogenAtom(0, 6.0, 5000)
atom.solve()

eigenvalue = atom.getEigenvalue()
print(f'Ground state energy: {eigenvalue} Hartree')
position = atom.getPosition()
wave = atom.getEigenstate()

write_dat(position, wave, 'wave_function.dat')
print('Ground state wave function saved to wave_funtion.dat')
plt.plot(position, wave, 'o-')
plt.xlabel('r (Bohr)')
plt.ylabel(r'$\psi(r)$')
plt.savefig('psi0.eps', bbox_inches='tight')

# Shutdown the JVM
# jpype.shutdownJVM()
