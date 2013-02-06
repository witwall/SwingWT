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

import org.apache.bcel.*;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.commons.lang.*;

public class SwingTransformer extends EmptyVisitor
{
	public static String MY_PACKAGE_PATH= updatePackage("swingwt");
	public static String MY_AWT_PATH;
	public static String MY_SWING_PATH;
	public static String MY_AWT_PACKAGE;
	public static String MY_SWING_PACKAGE;

	public static final String AWT_PATH= "java/awt/";
	public static final String SWING_PATH= "javax/swing/";
	public static final String AWT_PACKAGE= "java.awt.";
	public static final String SWING_PACKAGE= "javax.swing.";

	private InstructionHandle instructionHandle;
	private boolean changed;
	private Instruction instruction;
	private InstructionFactory factory;
	private ConstantPoolGen constantPoolGen;

	public JavaClass transform(JavaClass javaClass)
	{
		String superclassName= javaClass.getSuperclassName();
		if (isSwingAwtClass(superclassName))
			javaClass.setSuperclassName(transformSwingClassName(superclassName));

		javaClass.setInterfaceNames(transformInterfaces(javaClass.getInterfaceNames()));

		ClassGen classGen= new ClassGen(javaClass);
		constantPoolGen= classGen.getConstantPool();
		factory= new InstructionFactory(classGen);

		transformConstants();

		Method[] methods= classGen.getMethods();
		for (int i= 0; i < methods.length; i++)
		{
			changed= false;

			Method method= methods[i];

			Code code= method.getCode();

			if (code != null)
			{
				InstructionList instructionList= new InstructionList(code.getCode());
				instructionHandle= instructionList.getStart();
				while (instructionHandle != null)
				{
					instruction= instructionHandle.getInstruction();
					instruction.accept(this);
					instructionHandle= instructionHandle.getNext();
				}

				if (changed)
					code.setCode(instructionList.getByteCode());
			}

			MethodGen methodGen= new MethodGen(method, javaClass.getClassName(), constantPoolGen);
			methodGen.setReturnType(trasformSwingReference(method.getReturnType()));
			methods[i]= methodGen.getMethod();
			javaClass.setMethods(methods);
		}

		javaClass.setConstantPool(constantPoolGen.getFinalConstantPool());

		return javaClass;
	}

	private String[] transformInterfaces(String[] aStrings)
	{
		for (int i= 0; i < aStrings.length; i++)
			aStrings[i]= transformSwingClassName(aStrings[i]);

		return aStrings;
	}

	public void visitNEW(NEW obj)
	{
		NEW aNew= (NEW)instruction;

		NEW createInvoke= factory.createNew(new ObjectType(replaceSwing(aNew.getLoadClassType(constantPoolGen).getClassName())));
		instructionHandle.setInstruction(createInvoke);
		changed= true;
	}

	private static String replaceSwing(String string)
	{
		string= StringUtils.replace(StringUtils.replace(string, SWING_PACKAGE, MY_SWING_PACKAGE), SWING_PATH, MY_SWING_PATH);
		return string;
	}

	private static String replaceAwt(String string)
	{
		string= StringUtils.replace(StringUtils.replace(string, AWT_PACKAGE, MY_AWT_PACKAGE), AWT_PATH, MY_AWT_PATH);
		return string;
	}

	public void visitINVOKEVIRTUAL(INVOKEVIRTUAL obj)
	{
		changed= transformInvoke(obj, Constants.INVOKEVIRTUAL);
	}

	public void visitINVOKESPECIAL(INVOKESPECIAL obj)
	{
		changed= transformInvoke(obj, Constants.INVOKESPECIAL);
	}

	public void visitINVOKESTATIC(INVOKESTATIC obj)
	{
		InvokeInstruction iv= (InvokeInstruction)instruction;
		String methodName= iv.getMethodName(constantPoolGen);

		if (methodName.equals("forName"))
		{
			instructionHandle.setInstruction(factory.createInvoke(this.getClass().getName(), "forNameChanged", iv.getReturnType(constantPoolGen), iv.getArgumentTypes(constantPoolGen), Constants.INVOKESTATIC));
			changed= true;
		}
		else
			changed= transformInvoke(obj, Constants.INVOKESTATIC);
	}

	private boolean isSwingAwtClass(String methodClassName)
	{
		return methodClassName.startsWith(SWING_PACKAGE) || methodClassName.startsWith(AWT_PACKAGE);
	}

	private void transformConstants()
	{
		for (int i= 0; i < constantPoolGen.getSize(); i++)
		{
			Constant constant= constantPoolGen.getConstant(i);

			if (constant instanceof ConstantUtf8)
			{
				ConstantUtf8 utf8= (ConstantUtf8)constant;
				utf8.setBytes(transformSwingClassName(utf8.getBytes()));
			}
		}
	}

	private boolean transformInvoke(InvokeInstruction iv, short invokeType)
	{
		String methodClassName= iv.getClassName(constantPoolGen);

		if (isSwingAwtClass(methodClassName))
		{
			methodClassName= transformSwingClassName(methodClassName);
			Type returnType= trasformSwingReference(iv.getReturnType(constantPoolGen));

			Type[] argumentTypes= iv.getArgumentTypes(constantPoolGen);
			for (int j= 0; j < argumentTypes.length; j++)
				argumentTypes[j]= trasformSwingReference(argumentTypes[j]);

			InvokeInstruction createInvoke= factory.createInvoke(methodClassName, iv.getMethodName(constantPoolGen), returnType, argumentTypes, invokeType);
			instructionHandle.setInstruction(createInvoke);
			changed= true;
		}
		return changed;
	}

	public static String transformSwingClassName(String methodClassName)
	{
		methodClassName= replaceAwt(methodClassName);
		methodClassName= replaceSwing(methodClassName);

		return methodClassName;
	}

	private Type trasformSwingReference(Type returnType)
	{
		if (returnType instanceof ObjectType)
			returnType= new ObjectType(transformSwingClassName(((ObjectType)returnType).getClassName()));

		return returnType;
	}

	public static Class forNameChanged(String aString, boolean initIgnored, ClassLoader ignored) throws ClassNotFoundException
	{
		// FIXME ignored params
		return SwingWTClassLoader.getInstance().loadClass(transformSwingClassName(aString));
	}

	public static Class forNameChanged(String aString) throws ClassNotFoundException
	{
		return SwingWTClassLoader.getInstance().loadClass(transformSwingClassName(aString));
	}


	public void visitINSTANCEOF(INSTANCEOF anIo)
	{
		INSTANCEOF createdInstanceOf= factory.createInstanceOf((ReferenceType)trasformSwingReference(anIo.getType(constantPoolGen)));
		instructionHandle.setInstruction(createdInstanceOf);
		changed= true;
	}

	public static String updatePackage(String aNewPackage)
	{
		MY_PACKAGE_PATH= aNewPackage;
		MY_AWT_PATH= MY_PACKAGE_PATH + "/awt/";
		MY_SWING_PATH= MY_PACKAGE_PATH + "x/swing/";
		MY_AWT_PACKAGE= MY_PACKAGE_PATH + ".awt.";
		MY_SWING_PACKAGE= MY_PACKAGE_PATH + "x.swing.";

		return aNewPackage;
	}
}
