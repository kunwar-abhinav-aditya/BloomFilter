## Build
The command `./gradlew clean build` will build the application.

## Running the application
Run the command `./gradlew bootRun` and the application will start at port `8080`.

## API

To make interaction simpler, there is an API to interact with the application.
The application exposes the endpoint `/spellchecker/check` to check whether a word is present 
or not.

The endpoint would thus be:
`http://localhost:8080/spellchecker/check?word=<word-to-check>`

Currently, the API doesn't support adding new words because the task asks only about implementing
the ability to check presence of a new word in the already existing lookup.

But the API is open to extension and this feature could also be easily added with small enhancements.

## Design decisions

The variables in a Bloom filter follow the following correlation:

```
n = ceil(m / (-k / log(1 - exp(log(p) / k))))
p = pow(1 - exp(-k / (m / n)), k)
m = ceil((n * log(p)) / log(1 / pow(2, log(2))))
k = round((m / n) * log(2))
```

If we decide to have a look-up dictionary with 1,000,000 words`(n)`, to obtain a false-positive 
probability rate of 0.0000001`(p)`, we need to have 23 hash functions applied`(k)`.
This will need around 33547705 bits in the filter (4MiB)`(m)`.

Thus for the given dataset of `338882` words and to obtain the `0.0000001` percent false-positive
rate, we have chosen to go with a filter size of `11368714` bits, and `23` hash functions.

Of course, this can be tuned based on our requirements.