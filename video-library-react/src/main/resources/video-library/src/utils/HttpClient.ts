type ClientParams<REQUEST> = {
  url: string,
  body?: REQUEST | null;
}

const httpMethod = {
  GET: 'GET',
  POST: 'POST',
  PUT: 'PUT',
  DELETE: 'DELETE'
} as const;
type HttpMethod = keyof typeof httpMethod

export interface HttpClient<REQUEST, RESPONSE> {
  get(params: ClientParams<REQUEST>): Promise<RESPONSE>;

  post(params: ClientParams<REQUEST>): Promise<RESPONSE>;

  put(params: ClientParams<REQUEST>): Promise<RESPONSE>;

  delete(params: ClientParams<REQUEST>): Promise<RESPONSE>;
}

export const fetchHttpClient = <REQUEST, RESPONSE>(): HttpClient<REQUEST, RESPONSE> => {
  const request = (method: HttpMethod) => (params: ClientParams<REQUEST>): Promise<RESPONSE> => {
    const requestOptions: RequestInit = {
      method: method,
      headers: {'Content-Type': 'application/json'},
      body: params.body ? JSON.stringify(params.body) : null
    };

    return fetch(params.url, requestOptions)
      .then((response: Response) => response.json());
  };
  return {
    get: request(httpMethod.GET),
    post: request(httpMethod.POST),
    put: request(httpMethod.PUT),
    delete: request(httpMethod.DELETE),
  };
};