package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration; // Explicit import for ConstructorDeclaration
import com.github.javaparser.ast.body.TypeDeclaration;

public class AutoGrader {

	// Test if the code defines a parameterized constructor and initializes the
	// object correctly
	public boolean testParameterizedConstructor(String filePath) throws IOException {
		System.out.println("Starting testParameterizedConstructor with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		boolean hasParameterizedConstructor = false;

		// Check for class creation (Person class)
		System.out.println("------ Class Creation ------");
		boolean personClassFound = false;
		for (TypeDeclaration<?> typeDecl : cu.findAll(TypeDeclaration.class)) {
			if (typeDecl.getNameAsString().equals("Person")) {
				System.out.println("Class 'Person' found: " + typeDecl.getName());
				personClassFound = true;
			}
		}

		if (!personClassFound) {
			System.out.println("Error: 'Person' class not found.");
			return false; // Early exit if class creation is missing
		}

		// Check for constructor definition
		System.out.println("------ Constructor Check ------");
		boolean parameterizedConstructorFound = false;

		// Explicitly specifying the type instead of using `var`
		for (ConstructorDeclaration constructor : cu.findAll(ConstructorDeclaration.class)) {
			if (constructor.getNameAsString().equals("Person")) {
				// Check if the constructor has parameters
				if (constructor.getParameters().size() == 1) {
					System.out.println("Parameterized constructor found: " + constructor.getName());
					parameterizedConstructorFound = true;
				}
			}
		}

		if (!parameterizedConstructorFound) {
			System.out.println("Error: Parameterized constructor not found.");
			return false; // Early exit if constructor is missing
		}

		// If class and constructor are found, return true
		System.out.println("Test passed: Parameterized constructor is correctly defined.");
		return true;
	}
}
