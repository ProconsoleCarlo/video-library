export enum Protocol {
  JPA = 'jpa',
  JDBC = 'jdbc',
  XLSX = 'xlsx'
}

export module Protocol {
  export const keys = Object.keys(Protocol) as Protocol[];
  export const values = Object.values(Protocol).filter(x => typeof x === 'string') as Protocol[];
}