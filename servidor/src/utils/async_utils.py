"""
Description: This module likely contains utility functions and tools
 for working with asynchronous operations, which can be helpful for 
 managing asynchronous tasks in your Flask application.
Purpose: To provide utilities for managing asynchronous operations.
"""
from threading import Thread

def run_task_in_background(task, **kwargs):
    thread = Thread(target=task, 
                    kwargs=dict(**kwargs))
    thread.start()