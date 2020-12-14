package dk.mwnck.rmi;


import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Permission;
import java.security.Policy;
import java.security.ProtectionDomain;

public class RmiServer
{
    /**
     * @param args the command line arguments
     */
    public static Registry registry;

    public RmiServer() throws RemoteException { }

    public static void main(String[] args)
    {
        try
        {
            System.out.println("RMI server localhost starts");

            // Create a server registry at default port 1099
            registry = LocateRegistry.createRegistry(1099);

            Policy allPermissionPolicy = new Policy() {

                @Override
                public boolean implies(ProtectionDomain domain, Permission permission) {
                    return true;
                }
            };

            Policy.getPolicy();
            Policy.setPolicy(allPermissionPolicy);
            System.setSecurityManager(new SecurityManager());

            System.out.println("RMI registry created ");

            // Create engine of remote services, running on the server
            RmiInterfaceImpl remoteEngine = new RmiInterfaceImpl();

            // Register the engine by a name
            String engineName = "Convert";
            Naming.rebind("//localhost/" + engineName, remoteEngine);
            System.out.println("Engine " + engineName + " bound in registry");

        }
        catch (Exception e)
        {
            System.err.println("Exception:" + e);
        }
    }
}


