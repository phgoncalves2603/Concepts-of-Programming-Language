# Project 1 – Simple Calculator (Lexical Analyzer + Parser)

## Overview
This project implements a **simple arithmetic calculator** using two core compiler components:

1. **Lexical Analyzer (Lexer)** – converts the input expression into tokens  
2. **Recursive-Descent Parser** – parses and evaluates the expression

The calculator works on a single-line input and supports integer arithmetic with correct operator precedence.

---

## Features
- Integer literals only
- Arithmetic operators:
  - Addition (`+`)
  - Subtraction (`-`)
  - Multiplication (`*`)
  - Division (`/`)
- Unary minus (e.g., `-5`, `-(2+3)`)
- Parenthesized expressions
- Operator precedence (`*` and `/` before `+` and `-`)
- Whitespace is ignored
- Syntax and runtime error handling

---

## Project Structure
```
LexicalAnalyzerProject/
│
├── src/
│   ├── lexer/
│   │   ├── LexicalAnalyzer.java
│   │   ├── Token.java
│   │   ├── TokenType.java
│   │   └── lexerTest
│   │
│   └── parser/
│       ├── Parser.java
│       └── parserTest
│   
│ 
│
├── input.txt
└── README.md
```

---

## Lexical Analyzer
The lexical analyzer scans the input expression character by character and produces tokens.

Each token contains:
- Token type (enum)
- Lexeme
- Row number
- Column number

Supported token types:
- Addition
- Subtraction
- Multiplication
- Division
- Left parenthesis
- Right parenthesis
- Integer literal
- End of string (EOS)

Whitespace characters are ignored.

---

## Parser
The parser is a **recursive-descent parser** built from a refactored grammar that removes left recursion.

### Grammar
```
Expression        → Term ExpressionPrime
ExpressionPrime   → + Term ExpressionPrime
                 | - Term ExpressionPrime
                 | ε

Term              → Factor TermPrime
TermPrime         → * Factor TermPrime
                 | / Factor TermPrime
                 | ε

Factor            → ( Expression )
                 | - Factor
                 | Number

Number            → Int-Lit
```

Each non-terminal corresponds to a method in the `Parser` class.  
Expression evaluation is performed during parsing.

---

## How to Run

1. Enter an arithmetic expression in `input.txt`
   ```
   (2 + 3) * -4
   ```

2. Compile the project:
   ```
   javac src/**/*.java
   ```

3. Run the program:
   ```
   java Main
   ```

4. Example output:
   ```
   Result = -20
   ```

---

## Error Handling
- Syntax errors report unexpected tokens
- Invalid characters are detected by the lexer
- Division by zero throws a runtime exception

---

