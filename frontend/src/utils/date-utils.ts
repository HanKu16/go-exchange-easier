export const getLocalDate = (dateWithTimeZone: string) => {
  const dateString = dateWithTimeZone;
  const dateObject = new Date(dateString);
  return dateObject.toLocaleDateString();
}