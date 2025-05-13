import 'package:client/components/media/book.dart';
import 'package:client/pages/actual_landing.dart';
import 'package:client/pages/create_page.dart';
import 'package:client/pages/explore/explore_page.dart';
import 'package:client/pages/home_page.dart';
import 'package:client/pages/landing_page.dart';
import 'package:client/pages/profile_page.dart';

import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'VibeVerse',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.black),
      ),
      initialRoute: '/actual_landing',
      routes: {
        '/home': (context) => const HomePage(),
        '/explore': (context) => const ExplorePage(),
        '/profile':
            (context) => const ProfilePage(
              username: 'libero31',
            ), 
        '/create': (context) => const CreatePage(),

        '/landing': (context) => const LandingPage(),
        '/actual_landing': (context) => const StartingPage(),
        '/book': (context) => const Book(),
      },
    );
  }
}
