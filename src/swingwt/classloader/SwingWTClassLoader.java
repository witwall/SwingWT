//WebOnSwing - Web Application Framework
//Copyright (C) 2004 Fernando Damian Petrola
//	
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//	
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//Lesser General Public License for more details.
//	
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package swingwt.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLEncoder;
import java.util.Hashtable;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

public class SwingWTClassLoader extends URLClassLoader
{
	protected static SwingWTClassLoader classLoader;

	public static SwingWTClassLoader getInstance()
	{
		if (classLoader == null)
			classLoader= new SwingWTClassLoader();

		if (Thread.currentThread().getContextClassLoader() != classLoader)
			Thread.currentThread().setContextClassLoader(classLoader);

		return classLoader;
	}

	protected boolean isSearching;
	protected ClassLoader parentClassLoader;
	protected Hashtable transformedClasses;

	protected SwingWTClassLoader()
	{
		super(new URL[] { }, Thread.currentThread().getContextClassLoader());
		parentClassLoader= Thread.currentThread().getContextClassLoader();
		transformedClasses= new Hashtable();
	}

	public Class findClass(String name) throws ClassNotFoundException
	{
		String fileName = name.replace('.', '/').concat(".class");
		
		URL resource= parentClassLoader.getResource(fileName);
		if (resource == null) {
				throw new ClassNotFoundException(name);
		}

		try
		{
			return transform(name, resource);
		}
		catch (Exception e)
		{
			throw new ClassNotFoundException(name, e);
		}
	}

	public Class loadClass(String name) throws ClassNotFoundException
	{
		return loadClass(name, true);
	}

	private boolean skipClass(String name)
	{
	    return (name.endsWith("SwingTransformer") || 
	    		name.endsWith("ClassLoader") || 
	    		name.startsWith(SwingTransformer.MY_PACKAGE_PATH) || 
	    		name.startsWith("java") || 
	    		name.startsWith("sun.") || 
	    		name.startsWith("org.xml.sax") ||
	    		name.startsWith("org.w3c") ||
	    		(name.startsWith("org.apache") && !name.startsWith("org.apache.batik")));
	}

	private Class transform(String name, URL resource) throws IOException, ClassFormatError
	{
		InputStream openStream= resource.openStream();
		
		ClassParser parser= new ClassParser(openStream, name);
		JavaClass javaClass= parser.parse();

		ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
		new SwingTransformer().transform(javaClass).dump(byteArrayOutputStream);
		byte[] byteArray= byteArrayOutputStream.toByteArray();

		return defineClass(name, byteArray, 0, byteArray.length);
	}

	protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		Object definedClass= transformedClasses.get(name);

		if (skipClass(name))
			return parentClassLoader.loadClass(name);
		else if (definedClass != null)
			return (Class)definedClass;
		else
		{
			Class foundClass= findClass(name);
			transformedClasses.put(name, foundClass);
			return foundClass;
		}
	}
}
