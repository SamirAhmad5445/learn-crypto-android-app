# Cryptography: Modes of Operation 🕵️‍♂️💻

Cryptography is like a secret language between two people, and the Modes of Operation are the rules that help them keep their conversation private. Imagine you're passing secret notes in class; these modes make sure no one else can read them!

## ECB (Electronic Codebook) 📖

- **What it is**: Think of ECB as using the same secret code for every sentence you write.
- **Pros**: Simple and fast.
- **Cons**: Not very secure. If you write "I like cats" ten times, it looks the same every time, making it easy to guess.

## CBC (Cipher Block Chaining) 🔗

- **What it is**: CBC is like adding a little bit of the previous secret note to your current one.
- **Pros**: Much more secure than ECB.
- **Cons**: A bit slower, and if one part gets messed up, it can affect the next one.

## CFB (Cipher Feedback) 🔄

- **What it is**: CFB is like whispering a secret, then using part of that secret to tell the next part.
- **Pros**: Can be used in real-time communication.
- **Cons**: Like a game of telephone, errors can propagate.

## OFB (Output Feedback) 🎤

- **What it is**: OFB turns your secret into a rhythm and uses that rhythm to keep the conversation going.
- **Pros**: Good for streaming data, like music or video.
- **Cons**: Must never reuse the rhythm, or it becomes predictable.

## GCM (Galois/Counter Mode) 🧮

- **What it is**: GCM is a math whiz kid; it uses fancy algebra to secure your notes and check them for any doodles or mistakes.
- **Pros**: Very secure and checks for tampering.
- **Cons**: More complex to understand.
