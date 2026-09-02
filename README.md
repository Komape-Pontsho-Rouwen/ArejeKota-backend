# Arejekota - Food Ordering System

A backend system for small food businesses in rural South Africa 
that currently manage orders via WhatsApp or walk ins.

## The Problem
Small restaurants in rural areas have no digital ordering system.
Customers travel long distances only to find items unavailable.
Vendors have no way to track orders, revenue, or business performance.

## The Solution
Arejekota gives vendors a platform to receive and manage orders,
and gives customers a way to order remotely — no travel, no queues.

## Features
- Customer self registration with OTP email verification
- JWT authentication — stay logged in across requests
- Role based access: Customer, Vendor, Cashier, Driver
- Account management: password reset, profile update, account deletion
- Employee management : vendors register and manage their own staff
- Auto-generated work emails for staff accounts
- Forced password change on first employee login

## Tech Stack
Java| Spring Boot | Spring Security | Spring Data JPA | 
MySQL | JWT | BCrypt | JavaMailSender

## Architecture
Controller | Service| Repository | DTOs |Custom exceptions

## Status
In active development : customer flow complete and tested via Postman.
Vendor and employee flows in progress.

