import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class SignUp extends StatefulWidget {
  const SignUp({super.key});

  @override
  State<SignUp> createState() => _SignUpPageState();
}

class _SignUpPageState extends State<SignUp> {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController usernameController = TextEditingController();
  final TextEditingController firstnameController = TextEditingController();
  final TextEditingController lastnameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController confirmPasswordController =
      TextEditingController();
  final TextEditingController dobController = TextEditingController();

  bool isLoading = false;

  Future<void> submitForm() async {
    if (_formKey.currentState!.validate()) {
      if (passwordController.text != confirmPasswordController.text) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Passwords do not match'),
            backgroundColor: Colors.red,
          ),
        );
        return;
      }

      setState(() => isLoading = true);

      final Map<String, dynamic> requestBody = {
        "username": usernameController.text,
        "email": emailController.text,
        "password": passwordController.text,
        "firstName": firstnameController.text,
        "lastName": lastnameController.text,
        "dateOfBirth": dobController.text,
      };

      final response = await http.post(
        Uri.parse('http://localhost:8080/api/vibers'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(requestBody),
      );

      setState(() => isLoading = false);

      if (response.statusCode == 201) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Signup successful!'),
            backgroundColor: Colors.green,
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Signup failed!'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 500,
      padding: EdgeInsets.all(24),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [BoxShadow(color: Colors.black12, blurRadius: 10)],
      ),
      child: Form(
        key: _formKey,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextFormField(
              controller: usernameController,
              decoration: InputDecoration(labelText: 'VibeTag'),
              validator:
                  (value) =>
                      value == null || value.isEmpty ? 'Enter VibeTag' : null,
            ),
            TextFormField(
              controller: firstnameController,
              decoration: InputDecoration(labelText: 'Firstname'),
              validator:
                  (value) =>
                      value == null || value.isEmpty
                          ? 'Enter First Name'
                          : null,
            ),
            TextFormField(
              controller: lastnameController,
              decoration: InputDecoration(labelText: 'Lastname'),
              validator:
                  (value) =>
                      value == null || value.isEmpty ? 'Enter Last Name' : null,
            ),
            TextFormField(
              controller: emailController,
              decoration: InputDecoration(labelText: 'Email'),
              validator:
                  (value) =>
                      value == null || !value.contains('@')
                          ? 'Enter valid email'
                          : null,
            ),
            TextFormField(
              controller: passwordController,
              obscureText: true,
              decoration: InputDecoration(labelText: 'Password'),
              validator:
                  (value) =>
                      value == null || value.length < 6
                          ? 'Minimum 6 characters'
                          : null,
            ),
            TextFormField(
              controller: confirmPasswordController,
              obscureText: true,
              decoration: InputDecoration(labelText: 'Confirm Password'),
            ),
            TextFormField(
              controller: dobController,
              decoration: InputDecoration(
                labelText: 'Date of Birth (YYYY-MM-DD)',
              ),
              validator:
                  (value) =>
                      value == null || value.isEmpty ? 'Enter DOB' : null,
            ),
            SizedBox(height: 20),
            isLoading
                ? CircularProgressIndicator()
                : ElevatedButton(
                  onPressed: submitForm,
                  child: Text('Start Vibing'),
                ),
          ],
        ),
      ),
    );
  }
}
