export interface ClientParams<REQUEST> {
  url: string,
  body?: REQUEST | null;
}

export enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE'
}

export interface HttpClient<REQUEST, RESPONSE> {
  get(params: ClientParams<REQUEST>): Promise<RESPONSE>;

  post(params: ClientParams<REQUEST>): Promise<RESPONSE>;

  put(params: ClientParams<REQUEST>): Promise<RESPONSE>;

  delete(params: ClientParams<REQUEST>): Promise<RESPONSE>;
}

export const fetchHttpClient = <REQUEST extends string, RESPONSE>(): HttpClient<REQUEST, RESPONSE> => {
  const request = (method: HttpMethod) => (params: ClientParams<REQUEST>): Promise<RESPONSE> => {
    const requestOptions: RequestInit = {
      method: method,
      headers: {'Content-Type': 'application/json'},
      body: params.body || null
    };

    return fetch(params.url, requestOptions)
      .then((response: Response) => {
        return response.json()
      });
  }
  return {
    get: request(HttpMethod.GET),
    post: request(HttpMethod.POST),
    put: request(HttpMethod.PUT),
    delete: request(HttpMethod.DELETE),
  }
}