```md
# QA Engineer — Take-Home Assignment

*A weekend project to assess how you approach QA work*

| Time expected | Around 8 hours : designed to fit a weekend, roughly 4 hours each |
|---|---|
| Deadline | Saturday 12:00 PM IST (afternoon - mid day) |
| How to submit | Public GitHub repo link emailed to rajdeep@uxistudio.in |
| Questions | rajdeep@uxistudio.in |

---

# The brief

Your task is to set up a QA process from scratch for a web application — strategy, framework, tests, CI, and bug reports. Treat this as the first week of a new job, compressed into a weekend.

We care more about your thinking than about volume. A focused, thoughtful submission beats a sprawling one every time.

# The application

You will test https://automationexercise.com — a public e-commerce practice site with both a UI and documented APIs.

Two pages on the site are useful starting points: /test_cases (the site's own pre-written scenarios) and /api_list (the API documentation). Use them as a baseline — but go beyond them. Anyone can copy 26 ready-made test cases. We want to see what you'd add.

This site is the only target for the assignment. All testing, automation, API calls, and bug reports must be against automationexercise.com. Do not substitute another website. This ensures your submission can be evaluated fairly alongside others.

# Before you start

## Use only fake data

Automation Exercise is a public third-party site. Anything you submit goes to them. Use throwaway email addresses, fake names, fake addresses, and fake card numbers (the site accepts any string of digits — never use a real card).

## If the site goes down

It's a free community site and is usually fine, but it's not enterprise infrastructure. If it's unavailable for more than an hour during your assignment window, email us with a screenshot showing the time. We'll either extend the deadline or provide an alternative. Don't lose hours silently.

---

# Ads and pop-ups

The site shows ads. Handling them is part of the exercise — real applications have similar quirks. How you deal with it is itself a signal.

# AI tools

Use them. Claude, ChatGPT, Copilot, Cursor — whatever helps. We don't see this as cheating; we see it as how modern engineers actually work. One thing to keep in mind: in the follow-up round we'll ask you to walk through your work and make live changes. So whatever you submit, make sure you understand it well enough to defend and extend it on the spot.

# Deliverables

Six items, all sitting in your GitHub repo. Use any tools, languages, or frameworks you're comfortable with — the stack is your choice, and justifying it is part of the work.

## 1. A short test strategy (about a page)

What you'd test, what you'd skip, and why. Top 3 risk areas you noticed. Your tool choices with a sentence each. What you'd tackle next if you had another week. This is the deliverable we read most carefully.

## 2. Test cases (15 of them)

A mix of positive, negative, and edge cases — Markdown, Sheets, or Excel is fine. Each with steps, expected result, and priority. Include at least 3 non-obvious ones — the kind of case a less experienced tester would miss.

## 3. A small automation framework

Built from scratch, your choice of stack. 8 automated UI tests and 3 API tests. Clean structure (Page Object Model or similar), test data in config files rather than hardcoded, and a basic HTML report on test runs.

## 4. A CI pipeline

A working GitHub Actions workflow that runs your tests on push and gives back an accessible report. Keep it simple — a basic pipeline that works is much better than an elaborate one that doesn't.

## 5. Three bug reports

There are bugs on this site — you'll find them. Write up three with title, severity, repro steps, expected vs. actual, and a screenshot if it helps. If you genuinely can't find three, document UX improvements you'd suggest instead and tell us why.

## 6. A README

How to run things locally, your architecture and tool choices, where you used AI and where you chose not to, the trade-offs you made because of the time limit, and what you'd do differently with more runway.

---

# Evaluation criteria

In order of how much weight each piece carries when we read your work.

| What we're looking at | Weight | What we mean |
|---|---|---|
| Test strategy & thinking | 25% | Reasoning, prioritization, tool justifications |
| Test case design | 20% | Risk-based thinking, non-obvious cases, clarity |
| Framework code & architecture | 20% | Could a teammate pick this up and extend it? |
| README & communication | 15% | Honest, clear, reflective writing |
| Bug reports | 10% | Depth of what you found, not just surface issues |
| CI pipeline | 10% | Does it run? Is it reasonable? |

# Scope and completion

This is designed to fit comfortably in 8 hours, and we hope you can complete all six deliverables. But if you don't — that's okay. We'd much rather see five thoughtful pieces than six rushed ones.

If you run out of time on something, say so in the README. Tell us what you'd have done with another two hours. We'll read what you submitted and judge what's there — not penalize you for what's missing. The thinking matters more than the checklist.

# Submitting your work

When you're done, push everything to a public GitHub repo and email the link to [CONTACT EMAIL] before the deadline. Inside the repo we'd expect to find:

- Your framework code and configs
- A docs/ folder with the strategy doc, test cases, and bug reports
- A working CI pipeline visible in the Actions tab
- A README in the repo root

---

# Final notes

We care more about how you think, learn, and explore than about ticking every box. If you try something ambitious and it doesn't quite work, tell us about it in the README — that's interesting to us. If you found the site frustrating in a particular way, tell us about that too.

If anything in this brief is unclear, email us before you start. We'd rather answer a question than have you guess.

Good luck — we're looking forward to seeing what you come up with.
```
