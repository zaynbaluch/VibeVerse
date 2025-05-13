import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
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
          const SnackBar(
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
            content: RText(text:'Signup successful!'),
            backgroundColor: AppColor.green,
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
           SnackBar(
            content: RText(text:"Signup failed!"),
            backgroundColor: AppColor.red,
          ),
        );
      }
    }
  }

  InputDecoration _inputDecoration(String label) {
    return InputDecoration(
      labelText: label,
      labelStyle: const TextStyle(color: Colors.grey),
      enabledBorder: const UnderlineInputBorder(
        borderSide: BorderSide(color: Colors.grey),
      ),
      focusedBorder: UnderlineInputBorder(
        borderSide: BorderSide(color: AppColor.green),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Flexible(
      child: SingleChildScrollView(
        child: SizedBox(
          width: 500,
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextFormField(
                  controller: usernameController,
                  style: const TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('VibeTag'),
                  validator:
                      (value) =>
                          value == null || value.isEmpty ? 'Enter VibeTag' : null,
                ),
                TextFormField(
                  controller: firstnameController,
                  style: const TextStyle(color:  Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('Firstname'),
                  validator:
                      (value) =>
                          value == null || value.isEmpty
                              ? 'Enter First Name'
                              : null,
                ),
                TextFormField(
                  controller: lastnameController,
                  style: const TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('Lastname'),
                  validator:
                      (value) =>
                          value == null || value.isEmpty ? 'Enter Last Name' : null,
                ),
                TextFormField(
                  controller: emailController,
                  style: const TextStyle(color:  Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('Email'),
                  validator:
                      (value) =>
                          value == null || !value.contains('@')
                              ? 'Enter valid email'
                              : null,
                ),
                TextFormField(
                  controller: passwordController,
                  obscureText: true,
                  style: const TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('Password'),
                  validator:
                      (value) =>
                          value == null || value.length < 6
                              ? 'Minimum 6 characters'
                              : null,
                ),
                TextFormField(
                  controller: confirmPasswordController,
                  obscureText: true,
                  style: const TextStyle(color:  Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('Confirm Password'),
                ),
                TextFormField(
                  controller: dobController,
                  style: const TextStyle(color:  Color.fromARGB(255, 255, 255, 255)),
                  decoration: _inputDecoration('Date of Birth (YYYY-MM-DD)'),
                  validator:
                      (value) =>
                          value == null || value.isEmpty ? 'Enter DOB' : null,
                ),
                const SizedBox(height: 10),
                isLoading
                    ? const CircularProgressIndicator()
                    : ElevatedButton(
                      onPressed: submitForm,
        
                      style: ElevatedButton.styleFrom(
                        backgroundColor: AppColor.green,
                        foregroundColor: Colors.white,
                      ),
                      child: RText(text: 'BECOME A VIBER'),
                    ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
