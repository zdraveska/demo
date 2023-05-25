package com.example.demo.domain.users;

import java.io.Serializable;

public record Name (String name, String lastName) implements Serializable  {
}
