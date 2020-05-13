package com.bearingpoint.bah.commands;

import com.bearingpoint.bah.datasource.DataSource;
import com.bearingpoint.bah.hero.Hero;
import com.bearingpoint.bah.server.Lobby;
import com.bearingpoint.bah.server.Marketplace;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class CommandFactory {

    private static final String COMMANDS_PACKAGE = "com.bearingpoint.bah.commands";
    private static final String COMMAND_INTERFACE = "Command";
    private static final String CHAIN_COMMAND_INTERFACE = "ChainCommand";
    private static final String DATASOURCE_COMMAND_INTERFACE = "DataSourceCommand";
    private static final String HERO_COMMAND_INTERFACE = "HeroCommand";
    private static final String LOBBY_COMMAND_INTERFACE = "LobbyCommand";
    private static final String MARKETPLACE_COMMAND_INTERFACE = "MarketplaceCommand";
    private static final String DOT = ".";
    private static final String SLASH = "/";
    private static final String CLASS_SUFFIX = ".class";
    private DataSource dataSource;
    private Lobby lobby;
    private Marketplace marketplace;

    public CommandFactory(DataSource dataSource, Lobby lobby, Marketplace marketplace) {
        this.dataSource = dataSource;
        this.lobby = lobby;
        this.marketplace = marketplace;
    }

    public Command createCommand(String commandName, List<String> parameters) {
        return createCommand(null, commandName, parameters);
    }

    public Command createCommand(Hero hero, String commandName, List<String> parameters) {
        return createCommand(COMMANDS_PACKAGE, COMMAND_INTERFACE, hero, commandName, parameters);
    }

    private static boolean doesImplement(Class<?> classObject, String interfaceName) {
        boolean result = false;
        Class<?>[] implementedInterfaces = classObject.getInterfaces();
        if (implementedInterfaces != null) {
            for (Class<?> implementedInterface : implementedInterfaces) {
                if (implementedInterface.getSimpleName().contains(interfaceName)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private Command createCommand(String scannedPackage, String interfaceName, Hero hero, String commandName, List<String> parameters) {
        Command result = null;
        String scannedPath = scannedPackage.replace(DOT, SLASH) + SLASH;
        URL scannedUrl = this.getClass().getClassLoader().getResource(scannedPath);
        if (scannedUrl != null) {
            if (scannedUrl.getProtocol().equals("file")) {
                File scannedDir = new File(scannedUrl.getFile());
                if (scannedDir.isDirectory()) {
                    for (File file : scannedDir.listFiles()) {
                        if (result == null) {
                            result = createCommand(file, scannedPackage, interfaceName, hero, commandName, parameters);
                        }
                    }
                }
            } else if (scannedUrl.getProtocol().equals("jar")) {
                String jarFileName;
                String entryName;
                JarFile jf = null;
                try {
                    // build jar file name, then loop through zipped entries
                    jarFileName = URLDecoder.decode(scannedUrl.getFile(), "UTF-8");
                    jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));

                    jf = new JarFile(jarFileName);
                    Enumeration<JarEntry> jarEntries = jf.entries();
                    while (result == null && jarEntries.hasMoreElements()) {
                        entryName = jarEntries.nextElement().getName();
                        if (Pattern.matches(scannedPath + ".*class", entryName)) {
                            try {
                                entryName = entryName.replace(SLASH, DOT);
                                int endIndex = entryName.length() - CLASS_SUFFIX.length();
                                entryName = entryName.substring(0, endIndex);
                                Class<?> entryClass = Class.forName(entryName);

                                if (!entryClass.isInterface()) {
                                    if (interfaceName != null) {
                                        if (entryName.equals(commandName) && doesImplement(entryClass, interfaceName)) {
                                            result = createCommand(hero, entryClass, parameters);
                                        }
                                    }
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    jf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (jf != null) {
                        try {
                            jf.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return result;
    }

    private Command createCommand(File file, String scannedPackage, String interfaceName, Hero hero, String commandName, List<String> parameters) {
        Command result = null;
        String resource = scannedPackage + DOT + file.getName();
        if (result == null) {
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    result = createCommand(child, resource, interfaceName, hero, commandName, parameters);
                }
            } else if (resource.endsWith(CLASS_SUFFIX)) {
                int endIndex = resource.length() - CLASS_SUFFIX.length();
                try {
                    String classFullPathName = resource.substring(0, endIndex); //classFullPathName = com.bp.bah.commands.VaultCommand (fara .class)
                    Class<?> classObject = Class.forName(classFullPathName);    
                    if (!classObject.isInterface()) {
                        String className = classFullPathName.substring(classFullPathName.lastIndexOf(DOT) + 1); //className = VaultCommand
                        if (className.contains(COMMAND_INTERFACE)) {    //className contine "Command"
                            String commandClassName = className.substring(0, className.indexOf(COMMAND_INTERFACE)).toLowerCase(); //commandClassName = vault (comanda pe care o cercetam din pachet)
                            if (isWantedCommand(commandClassName, commandName) && doesImplement(classObject, interfaceName)) {
                                result = createCommand(hero, classObject, parameters);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private boolean isWantedCommand(String commandClassName, String commandName) {
        return commandClassName.equals(commandName) || commandClassName.startsWith(commandName);
    }

    private Command createCommand(Hero hero, Class<?> classObject, List<String> parameters) {
        Command result = null;
        try {
            result = (Command) classObject.newInstance();
            result.setParameters(parameters);
            for (Class<?> cls : classObject.getInterfaces()) {
                switch (cls.getName()) {
                    case COMMANDS_PACKAGE + DOT + CHAIN_COMMAND_INTERFACE:
                        ((ChainCommand) result).setCommandFactory(this);
                        break;
                    case COMMANDS_PACKAGE + DOT + DATASOURCE_COMMAND_INTERFACE:
                        ((DataSourceCommand) result).setDataSource(dataSource);
                        break;
                    case COMMANDS_PACKAGE + DOT + HERO_COMMAND_INTERFACE:
                        ((HeroCommand) result).setHero(hero);
                        break;
                    case COMMANDS_PACKAGE + DOT + LOBBY_COMMAND_INTERFACE:
                        ((LobbyCommand) result).setLobby(lobby);
                        break;
                    case COMMANDS_PACKAGE + DOT + MARKETPLACE_COMMAND_INTERFACE:
                        ((MarketplaceCommand) result).setMarketplace(marketplace);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
