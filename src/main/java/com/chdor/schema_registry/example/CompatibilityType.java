package com.chdor.schema_registry.example;

public enum CompatibilityType {
	
	BACKWARD,
	BACKWARD_TRANSITIVE,
	FORWARD,
	FORWARD_TRANSITIVE,
	FULL,
	FULL_TRANSITIVE,
	NONE;
}
