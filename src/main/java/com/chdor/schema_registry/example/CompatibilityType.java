package com.chdor.schema_registry.example;

/**
 * Schema Registry Compatibility Strategy
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */
public enum CompatibilityType {
	
	BACKWARD,
	BACKWARD_TRANSITIVE,
	FORWARD,
	FORWARD_TRANSITIVE,
	FULL,
	FULL_TRANSITIVE,
	NONE;
}
