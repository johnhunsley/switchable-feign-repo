# switchable-feign-repo
An example of a feign client repository which allows for the feature to be switched on and off via Spring Profiles.

Feign provides a common configurable HTTP client and is used as a component within a repository bean to retrieve data from 
another service end point. Spring profiles allow the Feign client bean to be included or not in the context at boot. 
The Repository must handle the missing Feign client bean if not included and only attempt to make the request if the
Feign client exists. 

Each repository executes concurrently and results are collated after all have successfully returned. Repositories which
do not execute, because their Feign client bean does not exist, (i.e. they have been switched off), are simply skipped.
