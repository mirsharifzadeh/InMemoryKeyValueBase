# Custom In-Memory Key-Value Store (Redis Clone)

A lightweight, high-performance, multi-threaded in-memory key-value database built from scratch in core Java without any external dependencies or frameworks.

## 🚀 Overview

This project is a custom implementation of an in-memory data store inspired by Redis. It handles raw network communication via Java Sockets, supports concurrent client connections through a multi-threaded architecture, and implements key expiration using a hybrid TTL (Time-To-Live) mechanism.

---

## 🏗️ System Architecture & Logic

The application relies on a clear separation of concerns, dividing network listening, client handling, and background memory management across independent thread levels:

```text
[ Main Thread ]
       │
       ▼
[ RedisServerListenerThread ] ─── (Listens on port & accepts connections)
       │
       ├──► Client 1 ──► [ ClientHandlerThread 1 ] ──┐
       ├──► Client 2 ──► [ ClientHandlerThread 2 ] ──┼──► [ MemoryStore ]
       └──► Client 3 ──► [ ClientHandlerThread 3 ] ──┘         ▲
                                                               │
[ ExpiredKeyCleanerThread ] ────────────────────────────────────┘
  (Active background expiration sweep with interval sleeps)

