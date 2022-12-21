#### Sending HTTP Requests can be done the following ways

* An HTTP request parameter represents a simple way to send values from client to server in a key-value pair format. To send HTTP request parameters you append them to the URI in a request query expression
* HTTP request headers is similar to request parameters, sent in header but don't appear in URI
* Path variable sends data through the request path itself. It is the same for request parameter approach: You use a path variable to send a small quantity of data.
* The HTTP request body is mainly used to send larger quantity of data



##### Request parameters


* Common use case: Filtering or search conditions
* Say you have a product: You want users to search for color, size and style: these would be a good fit for request parameters
* When setting HTTP request parameters, you extend the path with a ? symbol. For example see below:

```aidl
@Controller
@RequestMapping("/home")
public String home(@RequestParam String color, Model page){ 
    page.addAttribute("username", "Katy");
    return "home.html";
    
    
http://localhost:8080/home?color=blue


```


#### Another example


```
@Controller
@RequestMapping("/")
public String home(@RequestParam String color, @RequestParam String size, Model page){
page.addAttribute("size, size);
page.addAttribute("color", color);
return "home.html";
}

http://localhost:8080/home?size=L&color=blue


```


### Path variables

* Using path variables is also a way of sending data from client to server. Instead of using the HTTP request parameters, you directly set variable values in path
* You don't identify the value with a key, you identify it with positioning
* Recommended only for mandatory variables
* Avoid setting too many
```aidl
# Request Parameters

http://localhost:8080/home?color=blue

# Path variables:
http://localhost:8080/home/blue

```


```aidl
@Controller
@RequestMapping("/home/{sizee}")
public String Main(@PathVariable String size, @RequestParameter String color, Page page){
page.addAttribute(color);
page.addAttribute(size);
return "home.html";

}

```



Quick review of HTTP methods:

> Get : the Client needs to get data from server

> Post: Client needs to send new data to server

> Put: Client needs to change an existing record

> Delete: Client request deletes data on the server ide

> Patch: Client needs to partially change a data record




