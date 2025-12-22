export const isInteger = (value: string | null | undefined): boolean => {
    if (value === null || value === undefined || value.trim() === '') {
        return false;
    }
    const num = Number(value);
    return Number.isInteger(num);
}